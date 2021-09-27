package com.example.demo.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.demo.model.SessionInfo;
import com.example.demo.model.response.SessionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {
    private static final Map<UUID, SessionInfo> USERID_TO_SESSIONS = new ConcurrentHashMap<>();
    private static final int SESSION_LIMIT_MINUTES = 1;

    public SessionResponse getSession(final UUID customerId) {
        SessionInfo sessionInfo = USERID_TO_SESSIONS.merge(customerId, new SessionInfo(customerId),
                (existingSession, newSession) -> existingSession.isSessionValid(SESSION_LIMIT_MINUTES) ?
                        existingSession :
                        newSession);

        return new SessionResponse(sessionInfo.getSessionId());
    }

    public Optional<SessionInfo> getSessionInfoById(final UUID sessionId) {
        return USERID_TO_SESSIONS.values().stream().filter(i -> i.getSessionId().equals(sessionId)).findFirst();
    }

}
