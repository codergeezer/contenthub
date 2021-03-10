package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.dto.request.ChangePasswordRequest;
import com.codergeezer.contenthub.dto.request.LoginRequest;
import com.codergeezer.contenthub.dto.request.SignUpRequest;
import com.codergeezer.contenthub.dto.response.LoginResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;

/**
 * @author haidv
 * @version 1.0
 */
public interface AuthService {

    void registerUser(SignUpRequest signUpRequest);

    LoginResponse generateToken(String grantType, String token, LoginRequest loginRequest);

    void logout(String token);

    void forgotPassword(String username) throws JSONException;

    void confirmForgotPassword(String token) throws JSONException;

    Object getUserInfo();

    void changePassword(ChangePasswordRequest request);
}
