package com.codergeezer.contenthub.auth;

import com.codergeezer.contenthub.entity.Contact;
import com.codergeezer.contenthub.entity.Credential;
import com.codergeezer.contenthub.entity.Group;
import com.codergeezer.contenthub.entity.User;
import com.codergeezer.contenthub.repository.ContactsRepository;
import com.codergeezer.contenthub.repository.CredentialRepository;
import com.codergeezer.contenthub.repository.GroupRepository;
import com.codergeezer.contenthub.repository.UserRepository;
import com.codergeezer.contenthub.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final ContactsRepository contactsRepository;

    private final UserRepository userRepository;

    private final CredentialRepository credentialRepository;

    private final GroupRepository groupRepository;

    @Autowired
    public CustomOAuth2UserService(ContactsRepository contactsRepository,
                                   UserRepository userRepository,
                                   CredentialRepository credentialRepository,
                                   GroupRepository groupRepository) {
        this.contactsRepository = contactsRepository;
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.groupRepository = groupRepository;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                                   oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new IllegalArgumentException("Email not found from OAuth2 provider");
        }

        Optional<Contact> contactsOpt = contactsRepository
                .findByContactAndTypeAndDeletedFalse(oAuth2UserInfo.getEmail(), Constant.EMAIL_CONTACT);
        Contact contact;
        User user;
        Credential credential;
        Group group = null;
        if (contactsOpt.isPresent()) {
            contact = contactsOpt.get();
            if (!contact.getProvider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
                throw new IllegalArgumentException("Looks like you're signed up with " +
                                                   contact.getProvider() + " account. Please use your " +
                                                   contact.getProvider() +
                                                   " account to login.");
            }
            user = userRepository.findByDeletedFalseAndUserId(contact.getUserId()).orElseThrow();
            credential = credentialRepository.findByDeletedFalseAndUserId(contact.getUserId()).orElseThrow();
            if (user.isBelongGroup()) {
                group = groupRepository.findByDeletedFalseAndAdminId(user.getUserId()).orElse(null);
            }
            return new UserPrincipal(user, credential, group, oAuth2User.getAttributes());
        } else {
            user = new User();
            user.setFullName(oAuth2UserInfo.getName());
            user.setAvatar(oAuth2UserInfo.getImageUrl());
            user = userRepository.save(user);
            credential = new Credential();
            credential.setUserName("123456");
            credential.setPassword("123456");
            credential.setUserCode(user.getCode());
            credential.setUserId(user.getUserId());
            credential.setTotalFailed((short) 0);
            credentialRepository.save(credential);
            contact = new Contact();
            contact.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
            contact.setProviderId(oAuth2UserInfo.getId());
            contact.setContact(oAuth2UserInfo.getEmail());
            contact.setType(Constant.EMAIL_CONTACT);
            contact.setUserId(user.getUserId());
            contact.setUserCode(user.getCode());
            if (Boolean.TRUE.equals(oAuth2UserInfo.getAttributes().get("email_verified"))) {
                contact.setVerified(true);
                contact.setVerifiedDate(LocalDateTime.now());
            }
            contactsRepository.save(contact);
            return new UserPrincipal(user, credential, null, oAuth2User.getAttributes());
        }
    }
}
