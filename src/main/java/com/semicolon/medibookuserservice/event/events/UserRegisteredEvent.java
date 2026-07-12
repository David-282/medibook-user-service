package com.semicolon.medibookuserservice.event.events;

import com.semicolon.medibookuserservice.enums.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisteredEvent {
    private UUID userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}