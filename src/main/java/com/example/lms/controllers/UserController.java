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
@RequestMapping("/api/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class UserController {

    UserService userService;

    @PutMapping("/profiles")
    BaseResponse<UserResponse> updateProfiles(@RequestParam Integer userId, @RequestBody UserUpdateRequest request) {
        UserResponse userResponse = userService.updateProfiles(userId ,request);
        return BaseResponse.of("Registration successful", userResponse);
    }
}
