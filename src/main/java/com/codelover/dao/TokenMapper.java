package com.codelover.dao;

import com.codelover.bean.Token;

public interface TokenMapper {
    int deleteByPrimaryKey(String tokenId);

    int insert(Token record);

    int insertSelective(Token record);

    Token selectByPrimaryKey(String tokenId);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);
}