package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.StakeInfo;
import com.example.demo.model.SessionInfo;
import com.example.demo.model.response.SessionResponse;
import com.example.demo.model.request.StakeRequest;
import com.example.demo.service.SessionService;
import com.example.demo.service.StakeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final SessionService sessionService;
    private final StakeService stakeService;

    @GetMapping("/session")
    public ResponseEntity<SessionResponse> getSession(@RequestParam UUID customerId) {
        log.debug("Session request");
        return ResponseEntity.ok(sessionService.getSession(customerId));
    }

    @PostMapping("/stake")
    public ResponseEntity<Void> acceptStake(@RequestParam UUID sessionId, @RequestBody StakeRequest stakeRequest) {
        log.debug("Stake request for session {} with stake {}", sessionId, stakeRequest);
        Optional<SessionInfo> sessionOptional = sessionService.getSessionInfoById(sessionId);
        if(sessionOptional.isEmpty()) {
            log.warn("Obsolete sessionId {}", sessionId);
            return ResponseEntity.badRequest().build();
        }
        stakeService.acceptStake(sessionOptional.get().getCustomerId(), stakeRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-max-stakes")
    public ResponseEntity<List<StakeInfo>> getMaxStakes(@RequestParam UUID betOfferId) {
        return ResponseEntity.ok(stakeService.getMaxStakes(betOfferId));
    }
}
