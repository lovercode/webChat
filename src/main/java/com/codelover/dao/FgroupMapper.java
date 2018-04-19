package com.codelover.dao;

import java.util.List;

import com.codelover.bean.Fgroup;

public interface FgroupMapper {
    int deleteByPrimaryKey(String groupId);

    int insert(Fgroup record);

    int insertSelective(Fgroup record);

    Fgroup selectByPrimaryKey(String groupId);

    int updateByPrimaryKeySelective(Fgroup record);

    int updateByPrimaryKey(Fgroup record);
    
    Fgroup selectByNameWithUser(String groupName, String userId);
    
    Fgroup selectByIdAndUser(String groupId, String userId);
    
    List<Fgroup> selectByUser(String userId);
}