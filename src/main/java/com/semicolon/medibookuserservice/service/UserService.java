package com.semicolon.medibookuserservice.service;

import com.semicolon.medibookuserservice.dto.request.CreateUserRequest;
import com.semicolon.medibookuserservice.dto.request.UpdateUserRequest;
import com.semicolon.medibookuserservice.dto.request.UpdateUserStatusRequest;
import com.semicolon.medibookuserservice.dto.response.UserResponse;
import com.semicolon.medibookuserservice.enums.Role;
import com.semicolon.medibookuserservice.enums.UserStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal")
    UserResponse getUserById(UUID id);

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse getUserByEmail(String email);

    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal")
    UserResponse updateUser(UUID id, UpdateUserRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse updateUserStatus(UUID id, UpdateUserStatusRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    List<UserResponse> getUsersByRole(Role role);

    @PreAuthorize("hasRole('ADMIN')")
    List<UserResponse> getUsersByStatus(UserStatus status);

    @PreAuthorize("hasRole('ADMIN')")
    List<UserResponse> getAllUsers();

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse disableUser(UUID id);
}
