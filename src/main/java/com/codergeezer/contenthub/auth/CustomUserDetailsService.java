package com.codergeezer.contenthub.auth;

import com.codergeezer.contenthub.entity.Credential;
import com.codergeezer.contenthub.entity.Group;
import com.codergeezer.contenthub.entity.User;
import com.codergeezer.contenthub.repository.CredentialRepository;
import com.codergeezer.contenthub.repository.GroupRepository;
import com.codergeezer.contenthub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final CredentialRepository credentialRepository;

    private final GroupRepository groupRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository,
                                    CredentialRepository credentialRepository,
                                    GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential credential = credentialRepository.findByUserNameAndDeletedFalse(username).orElseThrow();
        User user = userRepository.findByDeletedFalseAndUserId(credential.getUserId()).orElseThrow();
        Group group = null;
        if (user.isBelongGroup()) {
            group = groupRepository.findByDeletedFalseAndAdminId(user.getUserId()).orElse(null);
        }
        return new UserPrincipal(user, credential, group);
    }
}