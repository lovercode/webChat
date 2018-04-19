package com.codelover.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codelover.bean.Fgroup;
import com.codelover.bean.Friend;
import com.codelover.bean.FriendKey;
import com.codelover.bean.GroupWithFriend;
import com.codelover.bean.User;
import com.codelover.service.FriendService;
import com.codelover.utils.Response;

@Controller
@RequestMapping(value="/friend")
public class FriendController {
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	FriendService friendService;
	/*
	 * 获取所有朋友
	 */
	@RequestMapping(value="",method = RequestMethod.GET)
	@ResponseBody
	public Response<List<GroupWithFriend>> getAllFriends()
	{
		try {
			return new Response<List<GroupWithFriend>>(friendService.getAllFriends());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response<List<GroupWithFriend>>(400,e.getMessage());
		}
	}
	
	/*
	 * 添加朋友
	 */
	@RequestMapping(value="",method = RequestMethod.POST)
	@ResponseBody
	public Response addFriend(@RequestBody Friend friend)
	{
		try {
			friendService.addFriend(friend);
			return new Response(200,"请求发送成功,请等待对方同意");
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
	
	/*
	 * 同意或者拒绝好友请求
	 */
	@RequestMapping(value="dealValidate",method = RequestMethod.POST)
	@ResponseBody
	public Response dealValidate(@RequestBody Friend friend)
	{
		try {
			if(friendService.dealValidate(friend))
			{
				return new Response(200,"已同意");
			}else {
				return new Response(200,"已拒绝");
			}
			
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
	
	/**
	 * 删除好友
	 * @param friend
	 * @return
	 */
	@RequestMapping(value="/deleteFriend",method = RequestMethod.POST)
	@ResponseBody
	public Response deleteFriend(@RequestBody Friend friend) {
		try {
			friendService.deleteFriend(friend);
			return new Response(200,"删除成功");
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
	
	@RequestMapping(value="/getFriendById",method = RequestMethod.POST)
	@ResponseBody
	public Response<User> getFriendById(@RequestBody FriendKey key)
	{
		try {
			return new Response<User>(200,"ok",friendService.getFriendById(key));
		} catch (Exception e) {
			return new Response<User>(400,e.getMessage());
		}
	}
}
