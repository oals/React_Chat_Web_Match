package com.example.chatx_match.service;

import com.example.chatx_match.dto.ChatRoomDto;

public interface MatchService {

    Long createChatRoom();

    void insertChatRoomMember(ChatRoomDto chatRoomDto);
}
