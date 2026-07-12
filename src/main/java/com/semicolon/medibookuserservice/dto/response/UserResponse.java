package com.semicolon.medibookuserservice.dto.response;

import com.semicolon.medibookuserservice.enums.Role;
import com.semicolon.medibookuserservice.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdAt;
}