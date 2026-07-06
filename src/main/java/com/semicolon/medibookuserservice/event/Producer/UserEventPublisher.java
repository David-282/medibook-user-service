package com.semicolon.medibookuserservice.event.Producer;

import com.semicolon.medibookuserservice.data.models.User;
import com.semicolon.medibookuserservice.event.events.UserCreatedEvent;
import com.semicolon.medibookuserservice.event.events.UserStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUserCreated(User user) {
        kafkaTemplate.send("user.created",
                new UserCreatedEvent(user.getId(), user.getEmail(), user.getRole()));
    }

    public void publishUserStatusChanged(User user) {
        kafkaTemplate.send("user.status-changed",
                new UserStatusChangedEvent(user.getId(), user.getStatus()));
    }
}