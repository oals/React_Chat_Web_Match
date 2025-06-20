package com.example.chatx_match.controller;

import com.example.chatx_match.kafka.producer.MatchProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchProducer matchProducer;

    @PostMapping("/matchStart")
    public ResponseEntity<String> send(@CookieValue(value = "memberId", required = false) String memberId) {
        if (memberId != null) {
            matchProducer.sendMessage("match-events", memberId);
        }
        return ResponseEntity.ok("Message sent: " + memberId);
    }


}
