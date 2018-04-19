package com.codelover.service;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codelover.bean.ChatRoom;
import com.codelover.bean.RoomUser;
import com.codelover.bean.User;
import com.codelover.dao.ChatRoomMapper;
import com.codelover.dao.RoomUserMapper;
import com.codelover.dao.UserMapper;

@Service
public class ChatRoomService {
	
	@Autowired
	ChatRoomMapper chatRoomMapper;
	@Autowired
	RoomUserMapper roomUserMapper;
	@Autowired
	UserMapper userMapper;
	

	
	@Autowired
	HttpServletRequest httpServletRequest;
	public List<ChatRoom> findAllChatRoom() {
		String userId = httpServletRequest.getSession().getAttribute("userId").toString();
		return chatRoomMapper.findMyChatRoom(userId);
	}
	
	/**
	 * 添加群
	 * @param chatRoom
	 * @return
	 * @throws Exception
	 */
	public boolean addChatRoom(ChatRoom chatRoom) throws Exception {
		if(chatRoom.getRoomName() == null) {
			throw new Exception("名字不能为空");
		}
		String userId = httpServletRequest.getSession().getAttribute("userId").toString();
		chatRoom.setCreateUser(userId);
		chatRoom.setRoomId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
		chatRoomMapper.insertSelective(chatRoom);
		RoomUser roomUser = new RoomUser();
		roomUser.setRoomId(chatRoom.getRoomId());
		roomUser.setUserId(userId);
		roomUser.setRoomUserId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
		roomUser.setUserType("创建者");
		roomUserMapper.insertSelective(roomUser);
		return true;	
	}
	
	/**
	 * 添加用户到群
	 * @param roomUser
	 * @return
	 * @throws Exception
	 */
	public boolean addChatRoomUser(RoomUser roomUser) throws Exception {
		String userName = httpServletRequest.getSession().getAttribute("user").toString();
		if(chatRoomMapper.selectByPrimaryKey(roomUser.getRoomId()) == null) {
			throw new Exception("该群不存在");
		}
		if(userMapper.selectByPrimaryKey(roomUser.getUserId()) == null) {
			throw new Exception("该用户不存在");
		}
		if(roomUserMapper.selectByUserRoom(roomUser.getUserId(), roomUser.getRoomId()) != null) {
			throw new Exception("该用户已经在该群");
		}
		
		roomUser.setRoomUserId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
		roomUser.setUserType(userName+"添加");
		roomUserMapper.insertSelective(roomUser);
		return true;
		
	}

	public boolean exitChatRoom(ChatRoom chatRoom) {
		String userId = httpServletRequest.getSession().getAttribute("userId").toString();
		roomUserMapper.deleteByUserRoom(userId,chatRoom.getRoomId());
		return true;
		
	}

	public List<RoomUser> getAllUserByRoom(ChatRoom chatRoom) throws Exception {
		String userId = httpServletRequest.getSession().getAttribute("userId").toString();
		if(roomUserMapper.selectByUserRoom(userId, chatRoom.getRoomId()) == null)
		{
			throw new Exception("你不是该成员,无法查看");
		}
		return roomUserMapper.selectByRoomId(chatRoom.getRoomId());
		
	}
	
}
