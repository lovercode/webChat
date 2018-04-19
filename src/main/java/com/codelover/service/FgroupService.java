package com.codelover.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codelover.bean.Fgroup;
import com.codelover.bean.Friend;
import com.codelover.bean.FriendKey;
import com.codelover.dao.FgroupMapper;
import com.codelover.dao.FriendMapper;


@Service
public class FgroupService {
	
	@Autowired
	FgroupMapper fgroupMapper;
	@Autowired
	FriendMapper friendMapper;
	/*
	 * 添加分组
	 */
	public boolean addGroup(Fgroup group) throws Exception {
		
		Fgroup group2 = fgroupMapper.selectByNameWithUser(group.getGroupName(), group.getUserId());
				
		if(group2 != null)
		{
			throw new Exception("你已经添加了该分组");
		}
		if(group.getGroupName()==null)
		{
			throw new Exception("名字不能为空");
		}
		group.setGroupId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
		fgroupMapper.insert(group);
		return true;
	}
	
	/*
	 * 更新分组
	 */
	public boolean updateGroup(Fgroup group) throws Exception
	{
		if(fgroupMapper.selectByIdAndUser(group.getGroupId(), group.getUserId()) == null) {
			throw new Exception("id错误");
		}
		if(fgroupMapper.selectByNameWithUser(group.getGroupName(), group.getUserId()) != null)
		{
			throw new Exception("你已经存在该分组");
		}
		fgroupMapper.updateByPrimaryKey(group);
		return true;
	}
	
	/*
	 * 删除盆友
	 * -1代表删除状态
	 */
	public boolean deleteGroup(Fgroup group) throws Exception 
	{
		if(fgroupMapper.selectByIdAndUser(group.getGroupId(), group.getUserId()) == null) {
			throw new Exception("id错误");
		}
		List<Friend> allFriend = friendMapper.selectByGroup(group.getGroupId());
		FriendKey key = new FriendKey();
		for(int i=0; i<allFriend.size(); i++) {
			key.setUserId(allFriend.get(i).getFriendId());
			key.setFriendId(allFriend.get(i).getUserId());
			friendMapper.deleteByPrimaryKey(key);
		}
		friendMapper.deleteByGroup(group.getGroupId());
		fgroupMapper.deleteByPrimaryKey(group.getGroupId());
		return true;
	}
}
