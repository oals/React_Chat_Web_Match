package com.example.chatx_match.kafka.consumer;

import com.example.chatx_match.dto.ChatRoomDto;
import com.example.chatx_match.kafka.manager.MatchManager;
import com.example.chatx_match.kafka.producer.MatchProducer;
import com.example.chatx_match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchConsumer {

    private final MatchManager matchManager;
    private final MatchProducer matchProducer;
    private final MatchService matchService;

    private final SimpMessagingTemplate messagingTemplate;


    @KafkaListener(topics = "match-events", groupId = "match-consumer-group")
    public void listen(String userId) {
        System.out.println("Consumed: " + userId);

        matchManager.tryMatch(userId).ifPresent(match -> {
            String matched = String.join(" vs ", match);
            matchProducer.sendMessage("match-results", matched);

            Long chatRoomId = matchService.createChatRoom();

            ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                    .chatRoomId(chatRoomId)
                    .chatRoomMemberList(match)
                    .build();

            matchService.insertChatRoomMember(chatRoomDto);

            String destination = "/topic/match/" + userId;
            messagingTemplate.convertAndSend(destination, chatRoomDto);

            System.out.println("Matched: " + matched);
        });
    }

}