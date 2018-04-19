package com.codelover.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.codelover.bean.Chat;
import com.codelover.bean.ChatRoom;
import com.codelover.bean.NeedDealMsg;
import com.codelover.bean.User;
import com.codelover.service.ChatService;
import com.codelover.service.NeedDealMsgService;
import com.codelover.utils.Response;

@Controller
@RequestMapping(value="/chat")
public class ChatController {
	
	@Autowired
	ChatService chatService;
	
	@Autowired
	NeedDealMsgService needDealMsgService;
	
	
	/*
	 * 发送消息给用户
	 */
	@RequestMapping(value="",method = RequestMethod.POST)
	@ResponseBody
	public Response sendMsgToUser(@RequestBody Chat chat) {
		try {
			chatService.sendMsgToUser(chat);
			return new Response(200,"ok");
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
	
	/*
	 * 获取当前聊天用户未读消息
	 */
	@RequestMapping(value="getByUser",method = RequestMethod.POST)
	@ResponseBody
	public Response<List<Chat>> getNoReadMsg(@RequestBody User user)
	{
		try {
			return new Response<List<Chat>>(200,"ok",chatService.findMsgWithUser(user));
		} catch (Exception e) {
			return new Response<List<Chat>>(400,e.getMessage());
		}
	}
	
	/*
	 * 获取历史与朋友聊天消息
	 */
	@RequestMapping(value="findHistoryMsg", method = RequestMethod.POST)
	@ResponseBody
	public Response<List<Chat>> findHistoryMsg(@RequestBody String jsonStr)
	{
		JSONObject object = new JSONObject(jsonStr);
		return new Response<List<Chat>>(200,"ok",chatService.findHistoryMsg(object.getString("userId"),object.getInt("pageNum")));
//		return chatService.findHistoryMsg(object.getString("userId"),object.getInt("pageNum"));
	}
	
	@RequestMapping(value="findRoomHistoryMsg", method = RequestMethod.POST)
	@ResponseBody
	public Response<List<Chat>> findRoomHistoryMsg(@RequestBody String jsonStr)
	{
		try {
			JSONObject object = new JSONObject(jsonStr);
			return new Response<List<Chat>>(200,"ok",chatService.findRoomHistoryMsg(object.getString("roomId"),object.getInt("pageNum")));
		} catch (Exception e) {
			return new Response<List<Chat>>(400,e.getMessage());
		}
	}
	
	/*
	 * 抖一抖
	 */
	@RequestMapping(value="sharkUser",method = RequestMethod.POST)
	@ResponseBody
	public Response sharkUser(@RequestBody Chat chat) {
		try {
			chatService.sharkUser(chat);
			return new Response(200,"ok");
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
	/*
	 * 发送消息给群
	 */
	@RequestMapping(value="sendMsgToChatRoom",method = RequestMethod.POST)
	@ResponseBody
	public Response sendMsgToChatRoom(@RequestBody Chat chat) {
		try {
			chatService.sendMsgToChatRoom(chat);
			return new Response(200,"ok");
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
	
	/*
	 * 获取当前聊天群的消息
	 */
	@RequestMapping(value="getByRoom",method = RequestMethod.POST)
	@ResponseBody
	public Response<List<Chat>> getNoReadMsg(@RequestBody ChatRoom chatRoom)
	{
		try {
			return new Response<List<Chat>>(200,"ok",chatService.findMsgByChatRoom(chatRoom));
		} catch (Exception e) {
			return new Response<List<Chat>>(400,e.getMessage());
		}
	}
	
	@RequestMapping(value="getNoReadMsg",method = RequestMethod.POST)
	@ResponseBody
	public Response<NeedDealMsg> getNeedDealMsg()
	{
		return new Response<NeedDealMsg>(needDealMsgService.getNeedMsg());
	}
	
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	@ResponseBody
    public Response<String>  fileUpload2(@RequestParam("file") CommonsMultipartFile file) throws IOException {
        String basePath = "/var/www/html/";
        String exUrl = "upload/"+new Date().getTime()+UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
        String path=basePath+exUrl;
        String url = "http://127.0.0.1/"+exUrl;
        File newFile=new File(path);
        file.transferTo(newFile);
        return new Response(200,"ok",url);
    }
	
	
	
	
}
