package com.semicolon.medibookuserservice.event.events;

import com.semicolon.medibookuserservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusChangedEvent {
    private UUID userId;
    private UserStatus newStatus;
}
