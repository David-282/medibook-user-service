package com.semicolon.medibookuserservice.event.Producer;

import com.semicolon.medibookuserservice.data.models.User;
import com.semicolon.medibookuserservice.event.events.UserProfileCreatedEvent;
import com.semicolon.medibookuserservice.event.events.UserStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUserCreated(User user) {
        kafkaTemplate.send("user.profile.created",
                new UserProfileCreatedEvent(user.getId(),user.getEmail()));
    }

    public void publishUserStatusChanged(User user) {
        kafkaTemplate.send("user.status-changed",
                new UserStatusChangedEvent(user.getId(), user.getStatus()));
    }
}