package com.imei666.android.mvp.model.dto;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class MessageDTO {
	@Id
	private long id;
	private String content;
	private String sendTime;
	private long senderId;
	private long recverId;
	private int status;   //阅读状态 是否已读。0为未读，1为已读
	private String senderName;
	private String recverName;  
	private int messageType;   //消息类型 0为文本，1为图片，2为音频，3为视频
	private boolean isOwnSend;   //是否是自己发送的
	private int sendStatus;   //发送状态  0发送中  1发送成功  2发送失败
	private String senderAvatar;
	private String recverAvatar;
	private String readTime;


	public String getSenderAvatar() {
		return senderAvatar;
	}

	public void setSenderAvatar(String senderAvatar) {
		this.senderAvatar = senderAvatar;
	}

	public String getRecverAvatar() {
		return recverAvatar;
	}

	public void setRecverAvatar(String recverAvatar) {
		this.recverAvatar = recverAvatar;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public boolean isOwnSend() {
		return isOwnSend;
	}

	public void setOwnSend(boolean ownSend) {
		isOwnSend = ownSend;
	}

	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getRecverName() {
		return recverName;
	}
	public void setRecverName(String recverName) {
		this.recverName = recverName;
	}
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public long getRecverId() {
		return recverId;
	}
	public void setRecverId(long recverId) {
		this.recverId = recverId;
	}

	public MessageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageDTO(long id, String content, String sendTime, long senderId, long recverId) {
		super();
		this.id = id;
		this.content = content;
		this.sendTime = sendTime;
		this.senderId = senderId;
		this.recverId = recverId;
	}
	@Generated(hash = 1491029588)
	public MessageDTO(long id, String content, String sendTime, long senderId, long recverId,
			int status, String senderName, String recverName, int messageType, boolean isOwnSend,
			int sendStatus, String senderAvatar, String recverAvatar, String readTime) {
		this.id = id;
		this.content = content;
		this.sendTime = sendTime;
		this.senderId = senderId;
		this.recverId = recverId;
		this.status = status;
		this.senderName = senderName;
		this.recverName = recverName;
		this.messageType = messageType;
		this.isOwnSend = isOwnSend;
		this.sendStatus = sendStatus;
		this.senderAvatar = senderAvatar;
		this.recverAvatar = recverAvatar;
		this.readTime = readTime;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public boolean getIsOwnSend() {
		return this.isOwnSend;
	}

	public void setIsOwnSend(boolean isOwnSend) {
		this.isOwnSend = isOwnSend;
	}
	
	
}
