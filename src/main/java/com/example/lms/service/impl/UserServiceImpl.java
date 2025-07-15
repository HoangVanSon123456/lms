package com.example.lms.service.impl;

import com.example.lms.dto.request.UserCreationRequest;
import com.example.lms.dto.request.UserUpdateRequest;
import com.example.lms.dto.response.UserResponse;
import com.example.lms.entity.Role;
import com.example.lms.entity.User;
import com.example.lms.entity.UserRole;
import com.example.lms.repository.RoleRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.repository.UserRoleRepository;
import com.example.lms.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService  {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserRoleRepository userRoleRepository;


    @Override
    public UserResponse register(UserCreationRequest request) {
        //Check email đã tồn tại
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .avatarUrl(request.getAvatarUrl())
                .isActive(true)
                .isLocked(false)
                .build();
        userRepository.save(user);

        Role role = roleRepository.findByName("MEMBER").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

        UserRole userRole = UserRole.builder()
                .userId(user.getId())
                .roleId(role.getId())
                .build();

        userRoleRepository.save(userRole);

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
