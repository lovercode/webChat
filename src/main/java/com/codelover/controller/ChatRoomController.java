package com.codelover.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codelover.bean.ChatRoom;
import com.codelover.bean.RoomUser;
import com.codelover.service.ChatRoomService;
import com.codelover.utils.Response;

@Controller
@RequestMapping(value="/chatRoom")
public class ChatRoomController {
	
	@Autowired
	ChatRoomService chatRoomService;
	
	/*
	 * 获取当前登录用户的所有群聊
	 */
	@RequestMapping(value="",method = RequestMethod.GET)
	@ResponseBody
	public Response<List<ChatRoom>> getMyChatRoom() {
		try {
			return new Response<List<ChatRoom>>(chatRoomService.findAllChatRoom());
		} catch (Exception e) {
			return new Response<List<ChatRoom>>(400,e.getMessage());
		}
	}
	
	/**
	 * 添加群
	 * @param chatRoom
	 * @return
	 */
	@RequestMapping(value="",method = RequestMethod.POST)
	@ResponseBody
	public Response addChatRoom(@RequestBody ChatRoom chatRoom)
	{
		try {
			chatRoomService.addChatRoom(chatRoom);
			return new Response(200,"添加成功");
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
	
	/**
	 * 添加群聊成员
	 * @param roomUser
	 * @return
	 */
	@RequestMapping(value="/addChatRoomUser",method = RequestMethod.POST)
	@ResponseBody
	public Response addChatRoom(@RequestBody RoomUser roomUser)
	{
		try {
			chatRoomService.addChatRoomUser(roomUser);
			return new Response(200,"添加成功");
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
	@RequestMapping(value="/exitChatRoom",method = RequestMethod.POST)
	@ResponseBody
	public Response exitChatRoom(@RequestBody ChatRoom chatRoom)
	{
		try {
			chatRoomService.exitChatRoom(chatRoom);
			return new Response(200,"退出成功");
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
	
	/**
	 * 获取群成员
	 * @param chatRoom
	 * @return
	 */
	@RequestMapping(value="/chatRoomUser",method = RequestMethod.POST)
	@ResponseBody
	public Response<List<RoomUser>> getAllUserByRoom(@RequestBody ChatRoom chatRoom)
	{
		try {
			return new Response<List<RoomUser>>(200,"ok",chatRoomService.getAllUserByRoom(chatRoom));
		} catch (Exception e) {
			return new Response<List<RoomUser>>(400,e.getMessage());
		}
	}
	
	
}
