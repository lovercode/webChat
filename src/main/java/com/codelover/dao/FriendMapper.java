package com.codelover.dao;

import java.util.List;

import com.codelover.bean.Friend;
import com.codelover.bean.FriendKey;

public interface FriendMapper {
    int deleteByPrimaryKey(FriendKey key);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(FriendKey key);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);
    
    int setFriendStatusByGroup(Integer status, String groupId);
    
    List<Friend> selectByGroup(String groupId);
    
    List<Friend> selectByStatus(String userId, Integer status);
    
    int deleteByGroup(String groupId);
    
   
}