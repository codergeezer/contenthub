package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.request.ChangePasswordRequest;
import com.codergeezer.contenthub.dto.request.LoginRequest;
import com.codergeezer.contenthub.dto.request.SignUpRequest;
import com.codergeezer.contenthub.dto.response.LoginResponse;
import com.codergeezer.contenthub.entity.*;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.repository.*;
import com.codergeezer.contenthub.auth.TokenProvider;
import com.codergeezer.contenthub.service.AuthService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.contenthub.utils.Constant;
import com.codergeezer.core.base.exception.BaseException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class AuthServiceImpl extends PrincipalProvider implements AuthService {

    private final UserRepository userRepository;

    private final CredentialRepository credentialRepository;

    private final ContactsRepository contactsRepository;

    private final LoginHistoryRepository loginHistoryRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final LockHistoryRepository lockHistoryRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           CredentialRepository credentialRepository,
                           ContactsRepository contactsRepository,
                           LoginHistoryRepository loginHistoryRepository,
                           PasswordEncoder passwordEncoder,
                           TokenProvider tokenProvider,
                           LockHistoryRepository lockHistoryRepository) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.contactsRepository = contactsRepository;
        this.loginHistoryRepository = loginHistoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.lockHistoryRepository = lockHistoryRepository;
    }

    @Transactional
    @Override
    public void registerUser(SignUpRequest signUpRequest) {
        if (contactsRepository
                .existsByContactAndTypeAndDeletedFalse(signUpRequest.getEmail(), Constant.EMAIL_CONTACT)) {
            throw new BaseException(ErrorCode.EMAIL_EXISTED);
        }
        if (credentialRepository.existsByDeletedFalseAndUserName(signUpRequest.getUsername())) {
            throw new BaseException(ErrorCode.USERNAME_EXISTED);
        }
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setFullName(signUpRequest.getFirstName() + StringUtils.SPACE + signUpRequest.getLastName());
        user = userRepository.save(user);
        Credential credential = new Credential();
        credential.setUserId(user.getUserId());
        credential.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        credential.setUserName(signUpRequest.getUsername());
        credential.setUserCode(user.getCode());
        credential.setTotalFailed((short) 0);
        credentialRepository.save(credential);
        Contact contact1 = new Contact();
        contact1.setUserId(user.getUserId());
        contact1.setContact(signUpRequest.getEmail());
        contact1.setType(Constant.EMAIL_CONTACT);
        contact1.setUserCode(user.getCode());
        contact1.setVerified(false);
        contact1.setPrimary(true);
        contactsRepository.save(contact1);
        if (!StringUtils.isBlank(signUpRequest.getPhone())) {
            Contact contact2 = new Contact();
            contact2.setUserId(user.getUserId());
            contact2.setContact(signUpRequest.getPhone());
            contact2.setUserCode(user.getCode());
            contact2.setVerified(false);
            contact1.setPrimary(false);
            contact2.setType(Constant.PHONE_CONTACT);
            contactsRepository.save(contact2);
        }
    }

    @Override
    public LoginResponse generateToken(String grantType, String token, LoginRequest loginRequest) {
        if (grantType.equalsIgnoreCase(Constant.PASSWORD_GRANT)) {
            return this.login(loginRequest);
        }
        if (grantType.equalsIgnoreCase(Constant.REFRESH_TOKEN_GRANT)) {
            return this.refreshToken(token);
        }
        throw new BaseException(ErrorCode.GRANT_NOT_SUPPORT);
    }

    @Override
    public void logout(String token) {
        Claims claims = tokenProvider.getAllClaimsFromToken(token);
        List<LoginHistory> loginHistories = loginHistoryRepository.findBySessionIdAndActivatedTrue(claims.getAudience())
                                                                  .stream().peek(v -> {
                    v.setActivated(false);
                    v.setEndAt(LocalDateTime.now());
                }).collect(Collectors.toList());
        loginHistoryRepository.saveAll(loginHistories);
    }

    @Override
    public void forgotPassword(String username) throws JSONException {
        Credential credential = credentialRepository.findByUserNameAndDeletedFalse(username)
                                                    .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        String confirmCode = RandomStringUtils.randomAlphanumeric(8);
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(30);
        contactsRepository.findByUserIdAndPrimaryTrueAndDeletedFalse(credential.getUserId())
                          .ifPresentOrElse(
                                  v -> lockHistoryRepository.findByUserIdAndLockTrueAndDeletedFalse(v.getUserId())
                                                            .ifPresentOrElse(v2 -> {
                                                                v2.setUnlockCodeExpired(dateTime);
                                                                v2.setUnlockCode(confirmCode);
                                                                lockHistoryRepository.save(v2);
                                                            }, () -> {
                                                                throw new BaseException(
                                                                        ErrorCode.NOT_FOUND_USER_LOCK_INFO);
                                                            }), () -> {
                                      throw new BaseException(ErrorCode.NOT_FOUND_USER_PRIMARY_EMAIL);
                                  });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("CONFIRM_CODE", confirmCode);
        jsonObject.put("USER_ID", credential.getUserId());
        String token = Base64.getEncoder().encodeToString(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Transactional
    @Override
    public void confirmForgotPassword(String token) throws JSONException {
        JSONObject jsonObject = new JSONObject(new String(Base64.getDecoder().decode(token)));
        String confirmCode = jsonObject.getString("CONFIRM_CODE");
        Long userId = jsonObject.getLong("USER_ID");
        String randomPass = RandomStringUtils.randomAlphanumeric(6);
        lockHistoryRepository.findByUserIdAndLockTrueAndDeletedFalse(userId)
                             .ifPresentOrElse(v -> {
                                 if (v.getUnlockCode().equals(confirmCode)) {
                                     throw new BaseException(ErrorCode.CONFIRM_CODE_NOT_MATCH);
                                 }
                                 if (v.getUnlockCodeExpired().isAfter(LocalDateTime.now())) {
                                     throw new BaseException(ErrorCode.CONFIRM_CODE_EXPIRED);
                                 }
                                 v.setLock(false);
                                 v.setUnlockTime(LocalDateTime.now());
                                 lockHistoryRepository.save(v);
                                 credentialRepository.findByDeletedFalseAndUserId(v.getUserId())
                                                     .ifPresentOrElse(v2 -> {
                                                         v2.setTotalFailed((short) 0);
                                                         v2.setPassword(passwordEncoder.encode(randomPass));
                                                         credentialRepository.save(v2);
                                                     }, () -> {
                                                         throw new BaseException(ErrorCode.USER_NOT_FOUND);
                                                     });
                             }, () -> {
                                 throw new BaseException(ErrorCode.NOT_FOUND_USER_LOCK_INFO);
                             });
    }

    @Override
    public Object getUserInfo() {
        return super.getCurrentPrincipal();
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequest request) {
        credentialRepository.findByUserNameAndDeletedFalse(super.getCurrentUsername())
                            .ifPresentOrElse(v -> {
                                if (passwordEncoder.matches(request.getCurrentPass(), v.getPassword())) {
                                    v.setPassword(passwordEncoder.encode(request.getNewPass()));
                                    credentialRepository.save(v);
                                    List<LoginHistory> loginHistories = loginHistoryRepository
                                            .findByUserIdAndDeletedFalseAndActivatedTrue(v.getUserId())
                                            .stream().peek(v2 -> {
                                                v2.setActivated(false);
                                                v2.setEndAt(LocalDateTime.now());
                                            }).collect(Collectors.toList());
                                    loginHistoryRepository.saveAll(loginHistories);
                                } else {
                                    throw new BaseException(ErrorCode.PASSWORD_NOT_MATCH);
                                }
                            }, () -> {
                                throw new BaseException(ErrorCode.USER_NOT_FOUND);
                            });
    }

    private LoginResponse login(LoginRequest loginRequest) {
        Credential credential = credentialRepository.findByUserNameAndDeletedFalse(loginRequest.getUsername())
                                                    .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        if (credential.getTotalFailed() >= 5) {
            throw new BaseException(ErrorCode.USER_HAS_LOCKED);
        }
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUserId(credential.getUserId());
        loginHistory.setUserCode(credential.getUserCode());
        loginHistory.setStartedAt(LocalDateTime.now());
        loginHistory.setRenewToken(false);
        if (!passwordEncoder.matches(loginRequest.getPassword(), credential.getPassword())) {
            loginHistory.setActivated(false);
            loginHistory.setSuccess(false);
            loginHistoryRepository.save(loginHistory);
            if (credential.getTotalFailed() == 4) {
                LockHistory lockHistory = new LockHistory();
                lockHistory.setLock(true);
                lockHistory.setUserId(credential.getUserId());
                lockHistory.setUserCode(credential.getUserCode());
                lockHistory.setLockTime(LocalDateTime.now());
                loginHistoryRepository.save(loginHistory);
            }
            credential.setTotalFailed((short) (credential.getTotalFailed() + 1));
            credentialRepository.save(credential);
            throw new BaseException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        if (credential.getTotalFailed() != 0) {
            credential.setTotalFailed((short) 0);
            credentialRepository.save(credential);
        }
        String securityCode = RandomStringUtils.randomAlphanumeric(8);
        loginHistory.setSecurityCode(securityCode);
        String uuId = UUID.randomUUID().toString();
        loginHistory.setSessionId(uuId);
        loginHistory.setSuccess(true);
        loginHistory.setActivated(true);
        loginHistory.setExpiredAt(tokenProvider.getAccessTokenExpired());
        loginHistoryRepository.save(loginHistory);
        LoginResponse loginResponse = tokenProvider.createToken(loginRequest.getUsername(), uuId);
        loginResponse.setSecurityCode(securityCode);
        return loginResponse;
    }

    private LoginResponse refreshToken(String token) {
        Claims claims = tokenProvider.getAllClaimsFromToken(token);
        String sessionId = claims.getAudience();
        String username = claims.getIssuer();
        if (!loginHistoryRepository.existsByActivatedTrueAndSessionIdAndRenewTokenFalse(sessionId)) {
            throw new BaseException(ErrorCode.USER_NOT_FOUND);
        }
        Credential credential = credentialRepository.findByUserNameAndDeletedFalse(username)
                                                    .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        String securityCode = RandomStringUtils.randomAlphanumeric(8);
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUserId(credential.getUserId());
        loginHistory.setUserCode(credential.getUserCode());
        loginHistory.setStartedAt(LocalDateTime.now());
        loginHistory.setRenewToken(true);
        loginHistory.setSecurityCode(securityCode);
        loginHistory.setSessionId(sessionId);
        loginHistory.setSuccess(true);
        loginHistory.setActivated(true);
        loginHistory.setExpiredAt(tokenProvider.getAccessTokenExpired());
        LoginResponse loginResponse = tokenProvider.createAccessToken(username, sessionId);
        loginHistoryRepository.findByRefreshTokenId(sessionId)
                              .ifPresent(v -> {
                                  if (v.getRenewToken()) {
                                      if (v.getExpiredAt().isBefore(LocalDateTime.now())) {
                                          v.setEndAt(v.getExpiredAt());
                                      } else {
                                          v.setEndAt(LocalDateTime.now());
                                      }
                                      v.setActivated(false);
                                      loginHistoryRepository.save(v);
                                  }
                              });
        loginHistoryRepository.save(loginHistory);
        loginResponse.setSecurityCode(securityCode);
        return loginResponse;
    }
}
