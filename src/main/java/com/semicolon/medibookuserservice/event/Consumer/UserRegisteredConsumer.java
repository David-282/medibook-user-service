package com.semicolon.medibookuserservice.event.Consumer;

import com.semicolon.medibookuserservice.dto.request.CreateUserRequest;
import com.semicolon.medibookuserservice.event.events.UserProfileCreatedEvent;
import com.semicolon.medibookuserservice.event.events.UserProfileCreationFailedEvent;
import com.semicolon.medibookuserservice.event.events.UserRegisteredEvent;
import com.semicolon.medibookuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredConsumer {

    private final UserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "user.registered", groupId = "user-service-group")
    public void consumeUserRegistration(UserRegisteredEvent event) {
        UUID userId = event.getUserId();

        log.info("Received registration event for user ID: {}", userId);
        if (userService.existsByEmail(event.getEmail())) {
            return;
        }

        if (userService.existsById(event.getUserId())) {
            log.warn("Duplicate event detected. User profile already exists for ID: {}. Skipping.", userId);
            return;
        }
        try {

            userService.createUser(new CreateUserRequest(
                    event.getEmail(),
                    event.getUserId(),
                    event.getFirstName(),
                    event.getLastName(),
                    event.getPhone(),
                    event.getRole()));

            UserProfileCreatedEvent successEvent = new UserProfileCreatedEvent(userId, event.getEmail());
            kafkaTemplate.send("user.profile.created", String.valueOf(userId), successEvent);
            log.info("Successfully processed profile and emitted success event for user: {}", userId);

        } catch (Exception exception) {
            log.error("Failed to build profile for user {}. Emitting compensation event. Error: {}", userId, exception.getMessage());

            UserProfileCreationFailedEvent failedEvent = new UserProfileCreationFailedEvent(userId, exception.getMessage());
            kafkaTemplate.send("user.registration.failed", String.valueOf(userId), failedEvent);
        }

    }
}
