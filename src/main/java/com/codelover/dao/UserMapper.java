package com.codelover.dao;

import java.util.List;

import com.codelover.bean.User;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);
    
    int updateMyInfo(User record);

    int updateByPrimaryKey(User record);
    
    User selectByUserName(String userName);
    
    List<User> selectByKey(String key);
}