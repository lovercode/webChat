package com.codelover.bean;

public class RoomUser {
    private String roomUserId;

    private String userId;

    private String userType;

    private String roomId;

    private String userName;
    
    private User userInfo;
    
    public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}
    public User getUserInfo() {
		return userInfo;
	}

    public String getRoomUserId() {
        return roomUserId;
    }

    public void setRoomUserId(String roomUserId) {
        this.roomUserId = roomUserId == null ? null : roomUserId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
}