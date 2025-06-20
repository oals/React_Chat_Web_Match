package com.example.chatx_match.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomDto {

    private Long chatRoomId;

    private Long memberId;

    private List<String> chatRoomMemberList;

    private String chatRoomCreateDate;

}
