package com.semicolon.medibookuserservice.utility;

import com.semicolon.medibookuserservice.data.models.User;
import com.semicolon.medibookuserservice.dto.response.UserResponse;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public UserResponse toResponse(User user){
        return  UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole())
                .status(user.getStatus())
//                .emailVerified(user.isEmailVerified())
                .createdAt(user.getCreatedAt())
                .build();
    }
}