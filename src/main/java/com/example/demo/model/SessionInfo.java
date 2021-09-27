package com.example.demo.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SessionInfo {
    private final UUID sessionId;
    private final UUID customerId;
    private final Long timeStamp;

    public SessionInfo(UUID customerId) {
        this.sessionId = UUID.randomUUID();
        this.timeStamp = System.currentTimeMillis();
        this.customerId = customerId;
    }

    public boolean isSessionValid(final int limitMinutes) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault()).plusMinutes(limitMinutes)
                .isAfter(LocalDateTime.now());
    }
}
