package com.codelover.bean;

import java.util.List;

public class GroupWithFriend {
	
	
	
	private Fgroup gFgroup;
	
	private List<Friend> friends;
	
	public GroupWithFriend() {
		// TODO Auto-generated constructor stub
	}
	public GroupWithFriend(Fgroup fgroup, List<Friend> friends){
		this.gFgroup = fgroup;
		this.friends = friends;
	}
	
	public List<Friend> getFriends() {
		return friends;
	}
	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}
	public Fgroup getgFgroup() {
		return gFgroup;
	}
	public void setgFgroup(Fgroup gFgroup) {
		this.gFgroup = gFgroup;
	}
	
}
