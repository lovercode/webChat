package com.codelover.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.codelover.bean.Chat;

public interface ChatMapper {
    int deleteByPrimaryKey(String chatId);

    int insert(Chat record);

    int insertSelective(Chat record);

    Chat selectByPrimaryKey(String chatId);

    int updateByPrimaryKeySelective(Chat record);

    int updateByPrimaryKey(Chat record);
    
    List<Chat> selectByFromTo(String fromId, String toId);

	List<Chat> selectHistoryMsg(@Param("userId")String userId, 
									@Param("myId")String myId, 
									@Param("pageNum")Integer pageNum,
									@Param("pageSize")Integer pageSize);

	List<Chat> selectByRoom(String roomId, String UserId);
	
	List<Chat> selectNeedDealMsg(String userId,String type);
	
	List<Chat> selectNeedDealRoomMsg(String userId,String type);

	List<Chat> selectRoomHistoryMsg(@Param("roomId")String roomId,
										@Param("pageNum")Integer pageNum,
										@Param("pageSize")Integer pageSize);
}