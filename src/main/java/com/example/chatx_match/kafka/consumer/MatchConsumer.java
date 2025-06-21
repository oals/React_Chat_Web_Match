package com.example.chatx_match.kafka.consumer;

import com.example.chatx_match.dto.ChatRoomDto;
import com.example.chatx_match.kafka.manager.MatchManager;
import com.example.chatx_match.kafka.producer.MatchProducer;
import com.example.chatx_match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
    public void listen(ConsumerRecord<String, String> record) {

        String userId = record.key();
        String payload = record.value();

        System.out.println("메세지 받음: " + userId);


        if (payload == null) {
            return;
        }

        matchManager.tryMatch(userId)
            .filter(match -> match.size() == 2 && !match.get(0).equals(match.get(1)))
            .ifPresent(match -> {
                String matched = String.join(" vs ", match);
                matchProducer.sendMessage("match-results", matched);

                Long chatRoomId = matchService.createChatRoom();

                ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                        .chatRoomId(chatRoomId)
                        .chatRoomMemberList(match)
                        .build();

                matchService.insertChatRoomMember(chatRoomDto);

                match.forEach(uid -> {
                    System.out.println("소켓 전송 경로 : /topic/match/" + uid);
                    String destination = "/topic/match/" + uid;
                    messagingTemplate.convertAndSend(destination, chatRoomDto);
                });

                System.out.println("매칭 완료: " + matched);
            });
    }

}