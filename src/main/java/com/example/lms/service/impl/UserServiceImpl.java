package com.example.lms.service.impl;

import com.example.lms.dto.request.UserUpdateRequest;
import com.example.lms.dto.response.UserResponse;
import com.example.lms.entity.User;
import com.example.lms.repositories.UserRepository;
import com.example.lms.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService  {

    UserRepository userRepository;

    @Override
    public UserResponse updateProfiles(Integer userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setFullName(request.getFullName());
        user.setAvatarUrl(request.getAvatarUrl());
        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
