package com.codelover.bean;

import java.util.List;

public class NeedDealMsg {

	//用户消息
	private List<Chat> userMsg;
	
	//群聊消息
	private List<Chat> chatRoomMsg;
	
	//用户抖一抖消息
	private List<Chat> userDouMsg;
	
	//验证消息
	private List<Friend> validateMsg;
	
	public void setUserDouMsg(List<Chat> userDouMsg) {
		this.userDouMsg = userDouMsg;
	}
	public List<Chat> getUserDouMsg() {
		return userDouMsg;
	}
	
	public void setUserMsg(List<Chat> userMsg) {
		this.userMsg = userMsg;
	}
	public List<Chat> getUserMsg() {
		return userMsg;
	}
	public void setChatRoomMsg(List<Chat> chatRoomMsg) {
		this.chatRoomMsg = chatRoomMsg;
	}
	public List<Chat> getChatRoomMsg() {
		return chatRoomMsg;
	}
	public void setValidateMsg(List<Friend> validateMsg) {
		this.validateMsg = validateMsg;
	}
	public List<Friend> getValidateMsg() {
		return validateMsg;
	}
}
