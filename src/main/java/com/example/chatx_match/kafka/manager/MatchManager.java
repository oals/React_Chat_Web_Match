package com.example.chatx_match.kafka.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class MatchManager {

    private final Queue<String> waitingQueue = new ConcurrentLinkedQueue<>();
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Set<String> waitingSet = ConcurrentHashMap.newKeySet();

    public Optional<List<String>> tryMatch(String userId) {
        // 이미 대기 중인 유저라면 무시
        if (!waitingSet.add(userId)) {
            return Optional.empty();
        }

        waitingQueue.add(userId);

        if (waitingQueue.size() < 2) return Optional.empty();

        String u1 = waitingQueue.poll();
        String u2 = waitingQueue.poll();
        waitingSet.remove(u1);
        waitingSet.remove(u2);

        if (u1.equals(u2)) {
            waitingQueue.add(u1);
            waitingSet.add(u1);
            return Optional.empty();
        }

        return Optional.of(List.of(u1, u2));
    }

}
