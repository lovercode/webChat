package com.codelover.controller;




import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.codelover.bean.Friend;
import com.codelover.bean.User;
import com.codelover.service.UserService;
import com.codelover.utils.Response;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/*
	 * 登录接口
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Response<User> login(@RequestBody User user,HttpServletRequest request) {

		try {
			userService.login(user);
			
			user.setUserPassword(null);
			return new Response<User>("登录成功",user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Response<User>(400,e.getMessage(),user);
		}
	}
	/*
	 * 注册接口
	 */
	@RequestMapping(value="",method = RequestMethod.POST)
	@ResponseBody
	public Response<User> register(@RequestBody User user)
	{
		try {
			userService.register(user);
			return new Response<User>("注册成功",user);
		} catch (Exception e) {
			return new Response<User>(400,e.getMessage(),user);
		}
		
	}
	
	/*
	 * 测试
	 */
	@RequestMapping(value="/test")
	@ResponseBody
	public Response<User> test(HttpServletRequest request){
		return new Response<User>(request.getRequestURI(),userService.getUserById("2"));
	}
	/*
	 * 退出接口
	 */
	@RequestMapping(value="/logout")
	@ResponseBody
	public Response logout(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		return new Response("退出成功");
		
	}
	/*
	 * 第三方登录
	 * github
	 */
	
	@ResponseBody
	@RequestMapping(value="/loginByToken")
	public Response<String> loginByToken(HttpServletRequest request,HttpServletResponse response)
	{
		
		try {
			if(userService.loginByToken(request))
			{
				response.sendRedirect("http://127.0.0.1/webChat/main.html");
				
			}else {
				return new Response<String>(400,"登录失败");
			}
			
		} catch (Exception e) {
			return new Response<String>(400,e.getMessage());
		}
		return null; 	
	}
	
	/*	
	 * 获取自己的信息
	 */
	@ResponseBody
	@RequestMapping(value="",method = RequestMethod.GET)
	public Response<User> getMyInfo(HttpServletRequest request)
	{
		User user = userService.getMyInfo(request);
		user.setUserPassword(null);
		return new Response<User>(user);
	}
	/*
	 * 搜索用户
	 */
	@RequestMapping(value="/search",method = RequestMethod.POST)
	@ResponseBody
	public Response<List<User>> searchFriend(@RequestBody String key)
	{
		try {
			JSONObject object = new JSONObject(key);
			return new Response<List<User>>(200,object.getString("key"),userService.searchFriend(object.getString("key")));
		} catch (Exception e) {
			return new Response<List<User>>(400,e.getMessage());
		}
	}
	/**
	 * 更新自己信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/updateMyInfo",method = RequestMethod.POST)
	@ResponseBody
	public Response updateMyInfo(@RequestBody User user) {
		try {
			userService.updateMyInfo(user);
			return new Response(200,"ok");
		} catch (Exception e) {
			return new Response(400,e.getMessage());
		}
	}
}
