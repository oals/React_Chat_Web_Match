package com.example.chatx_match.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MatchDao {
    Long insertChatRoom();

    void insertChatRoomMember(@Param("chatRoomId") Long chatRoomId, @Param("memberId") String memberId);
}
