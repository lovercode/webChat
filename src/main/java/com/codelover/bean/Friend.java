package com.codelover.bean;

import java.util.List;

public class Friend extends FriendKey {
    private String validateInfo;

    private Integer status;

    private String groupId;
    
    private User friendInfo;
    
    public void setFriendInfo(User friendInfo) {
		this.friendInfo = friendInfo;
	}
    public User getFriendInfo() {
		return friendInfo;
	}
    public String getValidateInfo() {
        return validateInfo;
    }

    public void setValidateInfo(String validateInfo) {
        this.validateInfo = validateInfo == null ? null : validateInfo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }
}