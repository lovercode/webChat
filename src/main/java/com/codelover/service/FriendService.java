package com.codelover.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.experimental.theories.Theories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codelover.bean.Fgroup;
import com.codelover.bean.Friend;
import com.codelover.bean.FriendKey;
import com.codelover.bean.GroupWithFriend;
import com.codelover.bean.User;
import com.codelover.dao.FgroupMapper;
import com.codelover.dao.FriendMapper;
import com.codelover.dao.UserMapper;
import com.codelover.utils.Response;

@Service
public class FriendService {
	
	@Autowired
	FriendMapper friendMapper;
	
	@Autowired
	FgroupMapper fgroupMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	HttpServletRequest request;
	
	/*
	 * 获取好友列表
	 */
	public List<GroupWithFriend> getAllFriends() {
		String userId = request.getSession().getAttribute("userId").toString();
		List<Fgroup> allGroup = fgroupMapper.selectByUser(userId);
		List<GroupWithFriend> res = new ArrayList<GroupWithFriend>();
		for(int i=0; i<allGroup.size(); i++)
		{
			res.add(new GroupWithFriend(allGroup.get(i),friendMapper.selectByGroup(allGroup.get(i).getGroupId())));
			
		}
		return res;
		
	}
	
	/*
	 * 添加好友
	 */
	public boolean addFriend(Friend friendKey) throws Exception
	{
		friendKey.setUserId(request.getSession().getAttribute("userId").toString());
		if(userMapper.selectByPrimaryKey(friendKey.getFriendId()) == null)
		{
			throw new Exception("没有这个用户"+friendKey.getFriendId());
		}
		FriendKey key = new FriendKey();
		key.setFriendId(friendKey.getFriendId());
		key.setUserId(friendKey.getUserId());
		Friend friend = friendMapper.selectByPrimaryKey(key);
		if(friend != null)
		{
			if(friend.getStatus() == 0)
			{
				throw new Exception("你已经发送请求，请等待对方同意");
			}else if(friend.getStatus() == 1){
				throw new Exception("该用户已经是你的好友了");
			}else {
				friend.setStatus(0);
				friendMapper.updateByPrimaryKey(friend);
				return true;
			}
		}else {
			if(fgroupMapper.selectByIdAndUser(friendKey.getGroupId(), request.getSession().getAttribute("userId").toString()) == null)
			{
				throw new Exception("你没有该分组");
			}
	
			friendKey.setFriendId(friendKey.getFriendId());
			friendKey.setUserId(friendKey.getUserId());
			friendKey.setStatus(0);
			friendMapper.insert(friendKey);
			return true;
		}
	}
	/**
	 * 处理好友请求
	 * @param friend
	 * @return
	 * @throws Exception
	 */
	public boolean dealValidate(Friend friend) throws Exception {
		
		String userId = request.getSession().getAttribute("userId").toString();
		if((friend.getStatus() == 1 || friend.getStatus() == -1) 
				&& friend.getFriendId() != null) {
		
			if(friend.getStatus() == 1) {
				if(fgroupMapper.selectByIdAndUser(friend.getGroupId(), userId) == null) {
					throw new Exception("你没有该分组");
				}
				FriendKey key = new  FriendKey();
				key.setUserId(userId);
				key.setFriendId(friend.getFriendId());
				if(friendMapper.selectByPrimaryKey(key) != null) {
					throw new Exception("你已经处理该请求，请勿重复提交");
				}
				friend.setValidateInfo("已处理");
				friend.setUserId(userId);
				friendMapper.insertSelective(friend);
				
				key.setUserId(friend.getFriendId());
				key.setFriendId(userId);
				Friend aim = friendMapper.selectByPrimaryKey(key);
				aim.setStatus(1);
				friendMapper.updateByPrimaryKey(aim);
				return true;
			}else {
				FriendKey key = new  FriendKey();
				key.setUserId(friend.getFriendId());
				key.setFriendId(userId);
				friendMapper.deleteByPrimaryKey(key);
				return false;
			}
			
		}else {
			throw new Exception("数据有误");	
		}
	}

	public boolean deleteFriend(Friend friend) throws Exception {
		String userId = request.getSession().getAttribute("userId").toString();
		FriendKey key = new FriendKey();
		key.setFriendId(friend.getFriendId());
		key.setUserId(userId);
		if(friendMapper.selectByPrimaryKey(key) == null) {
			throw new Exception("你们还不是好友");
		}
		friendMapper.deleteByPrimaryKey(key);
		key.setFriendId(userId);
		key.setUserId(friend.getFriendId());
		friendMapper.deleteByPrimaryKey(key);
		return true;	
	}

	public User getFriendById(FriendKey key) throws Exception {
		String userId = request.getSession().getAttribute("userId").toString();
		key.setUserId(userId);
		Friend friend = friendMapper.selectByPrimaryKey(key);
		/*if(friend == null) {
			throw new Exception("你们不是好友关系");
		}*/
		User user = userMapper.selectByPrimaryKey(key.getFriendId());
		user.setUserPassword("");
		return user;
	}

}
