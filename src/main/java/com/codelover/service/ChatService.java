package com.codelover.service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codelover.bean.Chat;
import com.codelover.bean.ChatRoom;
import com.codelover.bean.Friend;
import com.codelover.bean.FriendKey;
import com.codelover.bean.ReadRecord;
import com.codelover.bean.ReadRecordKey;
import com.codelover.bean.RoomUser;
import com.codelover.bean.User;
import com.codelover.dao.ChatMapper;
import com.codelover.dao.ChatRoomMapper;
import com.codelover.dao.FriendMapper;
import com.codelover.dao.ReadRecordMapper;
import com.codelover.dao.RoomUserMapper;
import com.codelover.dao.UserMapper;

@Service
public class ChatService {
	
	@Autowired
	ChatMapper chatMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	ReadRecordMapper readRecordMapper;
	
	@Autowired
	FriendMapper friendMapper;
	
	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Autowired
	ChatRoomMapper chatRoomMapper;
	
	@Autowired
	RoomUserMapper roomUserMapper;
	
	/*
	 * 发送消息给user
	 */
	public boolean sendMsgToUser(Chat chat) throws Exception
	{
		chat.setChatFrom(httpServletRequest.getSession().getAttribute("userId").toString());
		if(userMapper.selectByPrimaryKey(chat.getChatTo()) == null)
		{
			throw new Exception("用户不存在");
		}
		FriendKey friendKey = new FriendKey();
		friendKey.setFriendId(chat.getChatTo());
		friendKey.setUserId(chat.getChatFrom());
		Friend friend=null;
		if((friend=friendMapper.selectByPrimaryKey(friendKey)) == null)
		{
			throw new Exception("请先加对方为好友");
		}
		if(friend.getStatus() != 1) {
			throw new Exception("你和对方还不是好友关系");
		}
		chat.setChatId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
		chat.setChatType("普通消息");
		chat.setChatTime(System.currentTimeMillis());
		chatMapper.insert(chat);
		ReadRecord readRecord = new ReadRecord();
		readRecord.setChatId(chat.getChatId());
		readRecord.setStatus(0);
		readRecord.setUserId(chat.getChatTo());
		readRecordMapper.insert(readRecord);
		return true;
	}
	/*
	 * 获取当前聊天用户未读消息
	 */
	public List<Chat> findMsgWithUser(User user) throws Exception 
	{
		ReadRecordKey key = new ReadRecordKey();
		List<Chat> chatInfo = chatMapper.selectByFromTo(user.getUserId(), httpServletRequest.getSession().getAttribute("userId").toString());
		ReadRecord readRecord = new ReadRecord();
		for(int i=0; i<chatInfo.size(); i++)
		{
			readRecord.setChatId(chatInfo.get(i).getChatId());
			readRecord.setUserId(httpServletRequest.getSession().getAttribute("userId").toString());
			readRecord.setStatus(1);
			readRecordMapper.updateStatus(readRecord);
		}
		return chatInfo;
	}
	//查找历史消息
	public List<Chat> findHistoryMsg(String userId, Integer pageNum) {
		String myId = httpServletRequest.getSession().getAttribute("userId").toString();
		return chatMapper.selectHistoryMsg(userId, myId, pageNum*10, 10);
		
	}
	public List<Chat> findRoomHistoryMsg(String roomId, Integer pageNum) throws Exception {
		String userId = httpServletRequest.getSession().getAttribute("userId").toString();
		if(roomUserMapper.selectByUserRoom(userId, roomId) == null) {
			throw new Exception("你还不是该群成员");
		}
		String myId = httpServletRequest.getSession().getAttribute("userId").toString();
		return chatMapper.selectRoomHistoryMsg(roomId, pageNum*10, 10);
		
	}
	//抖一抖
	public boolean sharkUser(Chat chat) throws Exception {
		chat.setChatFrom(httpServletRequest.getSession().getAttribute("userId").toString());
		if(userMapper.selectByPrimaryKey(chat.getChatTo()) == null)
		{
			throw new Exception("用户不存在");
		}
		FriendKey friendKey = new FriendKey();
		friendKey.setFriendId(chat.getChatTo());
		friendKey.setUserId(chat.getChatFrom());
		Friend friend=null;
		if((friend=friendMapper.selectByPrimaryKey(friendKey)) == null)
		{
			throw new Exception("请先加对方为好友");
		}
		if(friend.getStatus() != 1) {
			throw new Exception("你和对方还不是好友关系");
		}
		chat.setChatId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
		chat.setChatType("抖一抖");
		chat.setChatTime(System.currentTimeMillis());
		chatMapper.insert(chat);
		ReadRecord readRecord = new ReadRecord();
		readRecord.setChatId(chat.getChatId());
		readRecord.setStatus(0);
		readRecord.setUserId(chat.getChatTo());
		readRecordMapper.insert(readRecord);
		return true;
		
	}
	//发送消息给群
	public boolean sendMsgToChatRoom(Chat chat) throws Exception {
		chat.setChatFrom(httpServletRequest.getSession().getAttribute("userId").toString());
		if(chatRoomMapper.selectByPrimaryKey(chat.getChatTo()) == null)
		{
			throw new Exception("该群不存在");
		}
		
		if(roomUserMapper.selectByUserRoom(chat.getChatFrom(),chat.getChatTo()) == null) {
			throw new Exception("你还不是该群成员");
		}
		chat.setChatId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
		chat.setChatType("群消息");
		chat.setChatTime(System.currentTimeMillis());
		chatMapper.insert(chat);
		List<RoomUser> allUser = roomUserMapper.selectByRoomId(chat.getChatTo());
		for(int i=0; i<allUser.size(); i++) {
			if(!allUser.get(i).getUserId().equals(chat.getChatFrom()))
			{
				ReadRecord readRecord = new ReadRecord();
				readRecord.setChatId(chat.getChatId());
				readRecord.setStatus(0);
				readRecord.setUserId(allUser.get(i).getUserId());
				readRecordMapper.insert(readRecord);
			}
		}
		return true;
		
	}
	/*
	 * 读取群未读消息
	 */
	public List<Chat> findMsgByChatRoom(ChatRoom chatRoom) throws Exception {
		String userId = httpServletRequest.getSession().getAttribute("userId").toString();
		if(roomUserMapper.selectByUserRoom(userId, chatRoom.getRoomId()) == null) {
			throw new Exception("你还不是该群成员");
		}
		List<Chat> allChat = chatMapper.selectByRoom(chatRoom.getRoomId(),userId);
		ReadRecord readRecord = new ReadRecord();
		for(int i=0; i<allChat.size(); i++) {
			//设置消息已读
			readRecord.setStatus(1);
			readRecord.setChatId(allChat.get(i).getChatId());
			readRecord.setUserId(userId);
			readRecordMapper.updateStatus(readRecord);
			if(allChat.get(i).getChatFrom().equals(userId)) {
				allChat.remove(i);
			}
		}
		return allChat;
	}
	
}
