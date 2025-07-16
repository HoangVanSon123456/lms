package com.example.lms.service;

import com.example.lms.dto.request.UserCreationRequest;
import com.example.lms.dto.request.UserUpdateRequest;
import com.example.lms.dto.response.UserResponse;

public interface UserService {
    UserResponse updateProfiles(Integer userId , UserUpdateRequest request);
}
