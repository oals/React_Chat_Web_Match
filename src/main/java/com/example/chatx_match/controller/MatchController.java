package com.example.chatx_match.controller;

import com.example.auth_common.resolver.AuthenticatedMemberId;
import com.example.chatx_match.kafka.producer.MatchProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchProducer matchProducer;

    @PostMapping("/api/match/start")
    public ResponseEntity<String> start(@AuthenticatedMemberId String memberId) {
        if (memberId != null) {
            matchProducer.sendMessage("match-events", memberId);
        }
        return ResponseEntity.ok("Message sent: " + memberId);
    }

    @DeleteMapping("/api/match/cancel")
    public ResponseEntity<String> cancel(@AuthenticatedMemberId String memberId) {
        if (memberId != null) {
            matchProducer.cancelMatchRequest(memberId);
        }

        return ResponseEntity.ok("Message sent: " + memberId);
    }
}
