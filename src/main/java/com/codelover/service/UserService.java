package com.codelover.service;

import static org.hamcrest.CoreMatchers.is;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codelover.bean.Token;
import com.codelover.bean.User;
import com.codelover.dao.TokenMapper;
import com.codelover.dao.UserMapper;
import com.codelover.utils.Md5;
import com.codelover.utils.Response;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private TokenMapper tokenMapper;
	
	@Autowired
	HttpServletRequest request;
	
	public User getUserById(String id)
	{
		return userMapper.selectByPrimaryKey(id);
	}
	
	/*
	 * 登录
	 */
	public boolean login(User user) throws Exception
	{
		User u_data = userMapper.selectByUserName(user.getUserName());
		if(u_data == null) {
			throw new Exception("没有该用户");
		}
		else if(!u_data.getUserPassword().equals(Md5.MD5(user.getUserPassword()))){
			user.setUserId(u_data.getUserId());
			throw new Exception("密码错误");
		}else {
			request.getSession().setAttribute("user", u_data.getUserName());
			request.getSession().setAttribute("userId", u_data.getUserId());
			u_data.setUserStatus("在线");
			userMapper.updateMyInfo(u_data);
			return true;
		}
	}
	/*
	 * 注册
	 */
	public boolean register(User user) throws Exception 
	{
		user.setUserId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
		user.setUserPassword(Md5.MD5(user.getUserPassword()));
		if(user.getUserName() == null)
		{
			throw new Exception("用户名不能为空");
		}
		else if(userMapper.selectByUserName(user.getUserName()) != null)
		{
			throw new Exception("该用户名已注册");
		}
		else 
		{
			userMapper.insertSelective(user);
			return true;
		}
	}
	/*
	 * 第三方登录
	 */
	public boolean loginByToken(HttpServletRequest request) throws Exception
	{
		String code = request.getParameter("code");
		String status = request.getParameter("state");
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://github.com/login/oauth/access_token";
		String data = "client_id=83a32c0d2be1fba49911&client_secret=d6f836dcc4f354745c7d29cd97bd9e4f8ecd7fa6&"+
		"code="+code+"&redirect_uri=http://127.0.0.1:8080/webChat/user/loginByToken&state="+status;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity entity = new HttpEntity(data,httpHeaders);
		String res = restTemplate.exchange(url,HttpMethod.POST,entity, String.class).getBody();
		String[] datas = res.split("&");
		String token = datas[0].split("=")[1];
		url = "https://api.github.com/user?access_token="+token;
		String reString = restTemplate.exchange(url, HttpMethod.GET,null,String.class).getBody();
		JSONObject d = new JSONObject(reString);
		Token token2 = new Token();
		Token token3 = tokenMapper.selectByPrimaryKey(d.getString("login")+d.getInt("id")+status);
		if(token3 != null)
		{
			User user = userMapper.selectByPrimaryKey(token3.getUserId());
			request.getSession().setAttribute("user", user.getUserName());
			request.getSession().setAttribute("userId", user.getUserId());
			user.setUserStatus("在线");
			userMapper.updateMyInfo(user);
			return true;
		}else {
			User user = new User();
			user.setUserId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
			token2.setUserId(user.getUserId());
			user.setUserName(d.getString("login"));
			while(userMapper.selectByUserName(user.getUserName()) != null)
			{
				user.setUserName(d.getString("login")+UUID.randomUUID().toString().replaceAll("-", "").substring(0, 3));
			}
			user.setUserRealName(d.getString("name"));
//			throw new Exception(res);
			if(!d.isNull("email"))
				user.setUserEmail(d.getString("email")+"");
			user.setUserStatus("在线");
			userMapper.insert(user);
			token2.setTokenId(d.getString("login")+d.getInt("id")+status);
			token2.setTokenName(status);
			token2.setTokenValue(token);
			token2.setTime(new Date().getTime());
			tokenMapper.insert(token2);
			request.getSession().setAttribute("user", user.getUserName());
			request.getSession().setAttribute("userId", user.getUserId());
			
			return true;
		}
	}
	
	public User getMyInfo(HttpServletRequest request) {
		return userMapper.selectByUserName(request.getSession().getAttribute("user").toString());
	}
	
	/*
	 * 搜索用户
	 */
	public List<User> searchFriend(String key) throws Exception {
		if(key.length() == 0)
		{
			throw new Exception("关键字不能为空");
		}
		return userMapper.selectByKey(key);
	}

	public boolean updateMyInfo(User user) {
		String userId = request.getSession().getAttribute("userId").toString();
		user.setUserId(userId);
		userMapper.updateMyInfo(user);
		return true;
	}
}
