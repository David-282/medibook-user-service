package com.semicolon.medibookuserservice.event.Consumer;

import com.semicolon.medibookuserservice.dto.request.CreateUserRequest;
import com.semicolon.medibookuserservice.event.Consumer.UserRegistrationConsumer;
import com.semicolon.medibookuserservice.event.events.UserRegisteredEvent;
import com.semicolon.medibookuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationConsumer {

    private final UserService userService;

    @KafkaListener(topics = "user.registration-requested", groupId = "user-service-group")
    public void onUserRegistrationRequested(UserRegisteredEvent event) {
        if (userService.existsByEmail(event.getEmail())) {
            return;
        }
        userService.createUser(new CreateUserRequest(
                event.getEmail(), event.getFirstName(), event.getLastName(),
                event.getPhone(), event.getRole()));
    }
}
