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
	@Generated(hash = 1799196218)
	public MessageDTO(long id, String content, String sendTime, long senderId, long recverId,
			int status, String senderName, String recverName, int messageType) {
		this.id = id;
		this.content = content;
		this.sendTime = sendTime;
		this.senderId = senderId;
		this.recverId = recverId;
		this.status = status;
		this.senderName = senderName;
		this.recverName = recverName;
		this.messageType = messageType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
