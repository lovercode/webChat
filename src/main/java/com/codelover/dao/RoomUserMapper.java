package com.codelover.dao;

import java.util.List;

import com.codelover.bean.RoomUser;

public interface RoomUserMapper {
    int deleteByPrimaryKey(String roomUserId);

    int insert(RoomUser record);

    int insertSelective(RoomUser record);

    RoomUser selectByPrimaryKey(String roomUserId);

    int updateByPrimaryKeySelective(RoomUser record);

    int updateByPrimaryKey(RoomUser record);

    RoomUser selectByUserRoom(String userId, String roomId);

	List<RoomUser> selectByRoomId(String chatTo);
	
	int deleteByUserRoom(String userId, String roomId);

    
}