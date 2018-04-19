package com.codelover.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codelover.bean.NeedDealMsg;
import com.codelover.dao.ChatMapper;
import com.codelover.dao.FriendMapper;

@Service
public class NeedDealMsgService {
	
	@Autowired
	ChatMapper chatMapper;
	
	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Autowired
	FriendMapper friendMapper;
	
	
	public NeedDealMsg getNeedMsg()
	{
		String userId = httpServletRequest.getSession().getAttribute("userId").toString();
		NeedDealMsg needDealMsg = new NeedDealMsg();
		needDealMsg.setUserMsg(chatMapper.selectNeedDealMsg(userId, "普通消息"));
		needDealMsg.setUserDouMsg(chatMapper.selectNeedDealMsg(userId, "抖一抖"));
		needDealMsg.setChatRoomMsg(chatMapper.selectNeedDealRoomMsg(userId, "群消息"));
		needDealMsg.setValidateMsg(friendMapper.selectByStatus(userId, 0));
		return needDealMsg;
	}
}
