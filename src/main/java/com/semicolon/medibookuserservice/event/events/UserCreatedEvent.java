package com.semicolon.medibookuserservice.event.events;

import com.semicolon.medibookuserservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {
    private String userId;
    private String email;
    private Role role;
}
