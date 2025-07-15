package com.example.lms.service.impl;

import com.example.lms.dto.request.UserCreationRequest;
import com.example.lms.dto.request.UserUpdateRequest;
import com.example.lms.dto.response.UserResponse;
import com.example.lms.entity.User;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService  {

    private final UserRepository userRepository;

    @Override
    public UserResponse register(UserCreationRequest request) {
        //Check email đã tồn tại
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .avatarUrl(request.getAvatarUrl())
                .build();

        user = userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

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
