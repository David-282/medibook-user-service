package com.semicolon.medibookuserservice.event.events;

import com.semicolon.medibookuserservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusChangedEvent {
    private String userId;
    private UserStatus newStatus;
}
