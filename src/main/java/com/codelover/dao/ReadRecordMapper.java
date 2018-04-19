package com.codelover.dao;

import com.codelover.bean.ReadRecord;
import com.codelover.bean.ReadRecordKey;

public interface ReadRecordMapper {
    int deleteByPrimaryKey(ReadRecordKey key);

    int insert(ReadRecord record);

    int insertSelective(ReadRecord record);

    ReadRecord selectByPrimaryKey(ReadRecordKey key);

    int updateByPrimaryKeySelective(ReadRecord record);

    int updateByPrimaryKey(ReadRecord record);
    
    int updateStatus(ReadRecord record);
}