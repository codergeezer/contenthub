package com.codergeezer.contenthub.controller;

import com.codergeezer.contenthub.dto.request.ChangePasswordRequest;
import com.codergeezer.contenthub.dto.request.LoginRequest;
import com.codergeezer.contenthub.dto.request.SignUpRequest;
import com.codergeezer.contenthub.dto.response.LoginResponse;
import com.codergeezer.contenthub.service.AuthService;
import com.codergeezer.core.base.data.ResponseData;
import com.codergeezer.core.base.data.ResponseUtils;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author haidv
 * @version 1.0
 */
@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/public/v1/auth/token")
    public ResponseEntity<ResponseData<LoginResponse>> generateToken(@RequestParam("grant_type") String grantType,
                                                                     @RequestParam(required = false) String token,
                                                                     @RequestBody(required = false) LoginRequest loginRequest) {
        return ResponseUtils.success(authService.generateToken(grantType, token, loginRequest));
    }

    @PostMapping("/api/public/v1/auth/signup")
    public ResponseEntity<ResponseData<Object>> registerUser(@RequestBody SignUpRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseUtils.created();
    }

    @DeleteMapping("/api/public/v1/auth/logout")
    public ResponseEntity<ResponseData<Object>> logout(@RequestParam String token) {
        authService.logout(token);
        return ResponseUtils.success();
    }

    @PostMapping("/api/public/v1/auth/forgot.password")
    public ResponseEntity<ResponseData<Object>> forgotPassword(@RequestParam String username) throws JSONException {
        authService.forgotPassword(username);
        return ResponseUtils.success();
    }

    @GetMapping("/api/public/v1/auth/forgot.password/confirm")
    public ResponseEntity<ResponseData<Object>> confirmForgotPassword(@RequestParam String token)
            throws URISyntaxException {
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            authService.confirmForgotPassword(token);
            httpHeaders.setLocation(new URI("https://www.google.com/"));
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (BaseException e) {
            httpHeaders.setLocation(new URI("https://www.google.com/"));
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (Exception e) {
            httpHeaders.setLocation(new URI("https://www.google.com/"));
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }
    }

    @PostMapping("/api/v1/me")
    public ResponseEntity<ResponseData<Object>> getUserInfo() {
        return ResponseUtils.success(authService.getUserInfo());
    }

    @PutMapping("/api/v1/me/change.password")
    public ResponseEntity<ResponseData<Object>> changePassword(@RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return ResponseUtils.success();
    }
}
