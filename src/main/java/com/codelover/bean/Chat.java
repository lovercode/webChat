package com.codelover.bean;

public class Chat {
    private String chatId;

    private String chatInfo;

    private String chatFrom;

    private String chatTo;

    private Long chatTime;

    private String chatType;
    
    private User fromUserInfo;
    
    private Integer sum;
    
    private ChatRoom chatRoom;
    
    public ChatRoom getChatRoom() {
		return chatRoom;
	}
    public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}
    
    public Integer getSum() {
		return sum;
	}
    public void setSum(Integer sum) {
		this.sum = sum;
	}
    
    public User getFromUserInfo() {
		return fromUserInfo;
	}
    public void setFromUserInfo(User fromUserInfo) {
		this.fromUserInfo = fromUserInfo;
	}

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId == null ? null : chatId.trim();
    }

    public String getChatInfo() {
        return chatInfo;
    }

    public void setChatInfo(String chatInfo) {
        this.chatInfo = chatInfo == null ? null : chatInfo.trim();
    }

    public String getChatFrom() {
        return chatFrom;
    }

    public void setChatFrom(String chatFrom) {
        this.chatFrom = chatFrom == null ? null : chatFrom.trim();
    }

    public String getChatTo() {
        return chatTo;
    }

    public void setChatTo(String chatTo) {
        this.chatTo = chatTo == null ? null : chatTo.trim();
    }

    public Long getChatTime() {
        return chatTime;
    }

    public void setChatTime(Long chatTime) {
        this.chatTime = chatTime;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType == null ? null : chatType.trim();
    }
}