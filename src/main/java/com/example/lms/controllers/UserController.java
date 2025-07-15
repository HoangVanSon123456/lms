package com.example.lms.controllers;

import com.example.lms.dto.request.UserCreationRequest;
import com.example.lms.dto.request.UserUpdateRequest;
import com.example.lms.dto.response.BaseResponse;
import com.example.lms.dto.response.UserResponse;
import com.example.lms.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class UserController {

    UserService userService;

//    @PostMapping("/register")
//    BaseResponse<UserResponse> register(@RequestBody UserCreationRequest request) {
//        UserResponse userResponse = userService.register(request);
//        return BaseResponse.of("Registration successful", userResponse);
//    }

    @PostMapping("/register")
    public String register() {
        return "OK";
    }

    @PutMapping("/profiles")
    BaseResponse<UserResponse> updateProfiles(@RequestParam Integer userId, @RequestBody UserUpdateRequest request) {
        UserResponse userResponse = userService.updateProfiles(userId ,request);
        return BaseResponse.of("Registration successful", userResponse);
    }
}
