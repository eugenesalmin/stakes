package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.response.SessionResponse;

@SpringBootTest
class SessionServiceTest {
    @Autowired
    private SessionService underTest;

    @Test
    public void testNewSessionCreated() {
        SessionResponse sessionResponse = underTest.getSession(UUID.randomUUID());
        assertNotNull(sessionResponse);
        assertNotNull(sessionResponse.getSessionId());
    }

    @Test
    public void testNewSessionCreatedAndPersisted() {
        UUID customerId = UUID.randomUUID();
        SessionResponse sessionResponse1 = underTest.getSession(customerId);
        assertNotNull(sessionResponse1);
        assertNotNull(sessionResponse1.getSessionId());
        SessionResponse sessionResponse2 = underTest.getSession(customerId);
        assertNotNull(sessionResponse2);
        assertNotNull(sessionResponse2.getSessionId());
        assertEquals(sessionResponse1, sessionResponse2);
    }

    @Test
    public void testValidateSession() {
        UUID customerId = UUID.randomUUID();
        SessionResponse sessionResponse1 = underTest.getSession(customerId);
        assertTrue(underTest.getSessionInfoById(sessionResponse1.getSessionId()).isPresent());
    }
}