package com.example.lms.service;

import com.example.lms.dto.request.AuthenticationRequest;
import com.example.lms.dto.request.UserCreationRequest;
import com.example.lms.dto.response.AuthenticationResponse;
import com.example.lms.dto.response.UserResponse;
import com.example.lms.entity.Role;
import com.example.lms.entity.User;
import com.example.lms.entity.UserRole;
import com.example.lms.repositories.RoleRepository;
import com.example.lms.repositories.UserRepository;
import com.example.lms.repositories.UserRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    public UserResponse register(UserCreationRequest request) {
        //Check email đã tồn tại
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
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

    public AuthenticationResponse login(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
