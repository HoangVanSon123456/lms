package com.example.lms.controllers;

import com.example.lms.dto.request.AuthenticationRequest;
import com.example.lms.dto.request.UserCreationRequest;
import com.example.lms.dto.response.AuthenticationResponse;
import com.example.lms.dto.response.BaseResponse;
import com.example.lms.dto.response.UserResponse;
import com.example.lms.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthenticationService authenticationService;

    @PostMapping("/register")
    BaseResponse<UserResponse> register(@RequestBody UserCreationRequest request) {
        UserResponse userResponse = authenticationService.register(request);
        return BaseResponse.of("Registration successful", userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
