package com.codelover.dao;

import java.util.List;

import com.codelover.bean.ChatRoom;

public interface ChatRoomMapper {
    int deleteByPrimaryKey(String roomId);

    int insert(ChatRoom record);

    int insertSelective(ChatRoom record);

    ChatRoom selectByPrimaryKey(String roomId);

    int updateByPrimaryKeySelective(ChatRoom record);

    int updateByPrimaryKey(ChatRoom record);

	List<ChatRoom> findMyChatRoom(String userId);
}