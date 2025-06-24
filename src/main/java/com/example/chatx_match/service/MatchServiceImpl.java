package com.example.chatx_match.service;

import com.example.chatx_match.dao.MatchDao;
import com.example.chatx_match.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{

    private final MatchDao matchDao;

    @Override
    public Long createChatRoom(ChatRoomDto chatRoomDto) {
        return matchDao.insertChatRoom(chatRoomDto);
    }

    @Override
    public void insertChatRoomMember(ChatRoomDto chatRoomDto) {

        for (String memberId : chatRoomDto.getChatRoomMemberList()) {
            matchDao.insertChatRoomMember(chatRoomDto.getChatRoomId(), memberId);
        }
    }
}
