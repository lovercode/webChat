package com.codelover.utils;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class BeforeAction extends HandlerInterceptorAdapter{
	List<String> allow_url = Arrays.asList("/webChat/user/login","/webChat/chat/upload","/webChat/user","/webChat/user/logout","/webChat/user/loginByToken");
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		header("Access-Control-Allow-Credentials: true");
//		header("Access-Control-Allow-Origin: http://www.xxx.com");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1");
		response.setHeader("Access-Control-Allow-Headers", "accept, Content-Type");
		if(request.getSession().getAttribute("user") != null || allow_url.contains(request.getRequestURI()))
		{
			return true;
		}else {
			response.setContentType("application/json;charset=UTF-8");
			Response res = new Response(302);
			response.getOutputStream().println(res.toString());
//			return true;
			return false;
		}
		
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
}
