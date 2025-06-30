package com.example.chatx_match.dao;

import com.example.chatx_match.dto.ChatRoomDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MatchDao {
    Long insertChatRoom(ChatRoomDto chatRoomDto);
}
