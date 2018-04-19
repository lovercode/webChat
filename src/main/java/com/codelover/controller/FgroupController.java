package com.codelover.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codelover.bean.Fgroup;
import com.codelover.service.FgroupService;
import com.codelover.utils.Response;

@Controller
@RequestMapping(value="/fgroup")
public class FgroupController {
	
	@Autowired
	FgroupService fgroupService;
	
	@Autowired
	HttpServletRequest request;
	
	
	/*
	 * 添加分组
	 */
	@RequestMapping(value="",method = RequestMethod.POST)
	@ResponseBody
	public Response addGroup(@RequestBody Fgroup group,HttpServletRequest request)
	{
		try {
			group.setUserId(request.getSession().getAttribute("userId").toString());
			fgroupService.addGroup(group);
			return new Response("添加成功");
		} catch (Exception e) {
			return  new Response(400,e.getMessage());
		}
	}
	
	@RequestMapping(value="",method = RequestMethod.PUT)
	@ResponseBody
	public Response updateGroup(@RequestBody Fgroup group)
	{
		try {
			group.setUserId(request.getSession().getAttribute("userId").toString());
			fgroupService.updateGroup(group);
			return new Response("更新成功");
		} catch (Exception e) {
			return  new Response(400,e.getMessage());
		}
	}
	
	/*
	 * 删除分组
	 */
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	@ResponseBody
	public Response deleteGroup(@RequestBody Fgroup group)
	{
		try {
			group.setUserId(request.getSession().getAttribute("userId").toString());
			fgroupService.deleteGroup(group);
			return new Response("删除成功");
		} catch (Exception e) {
			return  new Response(400,e.getMessage());
		}
	}
	
}
