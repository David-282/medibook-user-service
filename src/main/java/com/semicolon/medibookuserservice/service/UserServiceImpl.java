package com.semicolon.medibookuserservice.service;

import com.semicolon.medibookuserservice.data.models.User;
import com.semicolon.medibookuserservice.data.repository.UserRepository;
import com.semicolon.medibookuserservice.dto.request.CreateUserRequest;
import com.semicolon.medibookuserservice.dto.request.UpdateUserRequest;
import com.semicolon.medibookuserservice.dto.request.UpdateUserStatusRequest;
import com.semicolon.medibookuserservice.dto.response.UserResponse;
import com.semicolon.medibookuserservice.enums.Role;
import com.semicolon.medibookuserservice.enums.UserStatus;
import com.semicolon.medibookuserservice.event.Producer.UserEventPublisher;
import com.semicolon.medibookuserservice.exception.EmailAlreadyExistException;
import com.semicolon.medibookuserservice.exception.UserNotFoundException;
import com.semicolon.medibookuserservice.utility.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserEventPublisher eventPublisher;

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistException("User with email already exists");
        }

        User user = User.builder()
                .id(request.getId())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .role(request.getRole())
                .status(UserStatus.ACTIVE)
                .build();

        User saved = userRepository.save(user);

        eventPublisher.publishUserCreated(saved);

        return userMapper.toResponse(saved);
    }

    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        return userMapper.toResponse(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());

        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    public UserResponse updateUserStatus(UUID id, UpdateUserStatusRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        user.setStatus(request.getStatus());
        User saved = userRepository.save(user);

        eventPublisher.publishUserStatusChanged(saved);

        return userMapper.toResponse(saved);
    }

    public List<UserResponse> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(userMapper::toResponse)
                .toList();
    }


    public List<UserResponse> getUsersByStatus(UserStatus status) {
        return userRepository.findByStatus(status).stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public boolean existsById(UUID userId) {
        return userRepository.existsById(userId);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse disableUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        user.setStatus(UserStatus.DEACTIVATED);

        return userMapper.toResponse(userRepository.save(user));
    }
}
