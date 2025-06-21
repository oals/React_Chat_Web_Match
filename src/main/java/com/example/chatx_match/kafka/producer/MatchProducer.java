package com.example.chatx_match.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String memberId) {
        kafkaTemplate.send(topic, memberId, memberId);
        System.out.println("메세지 보냄: " + memberId);
    }

    public void cancelMatchRequest(String userId) {
        kafkaTemplate.send("match-events", userId, null);
    }

}