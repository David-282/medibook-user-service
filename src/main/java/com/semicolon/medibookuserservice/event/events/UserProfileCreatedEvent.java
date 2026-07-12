package com.semicolon.medibookuserservice.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileCreatedEvent {

    private UUID userId;
    private String email;
}
