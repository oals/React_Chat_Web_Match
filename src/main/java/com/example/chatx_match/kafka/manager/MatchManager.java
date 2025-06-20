package com.example.chatx_match.kafka.manager;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MatchManager {

    private final Queue<String> waitingQueue = new ConcurrentLinkedQueue<>();

    public Optional<List<String>> tryMatch(String userId) {
        if (waitingQueue.isEmpty()) {
            waitingQueue.add(userId);
            return Optional.empty();
        } else {
            String opponent = waitingQueue.poll();
            return Optional.of(List.of(userId, opponent));
        }
    }
}
