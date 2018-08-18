package com.imei666.android.mvp.model.dto;

import java.util.Date;

public class DiaryDTO {
	private long id;
	private String author;
	private Date publishTime;
	private String authorAvatar;
	private String coverImg;   //����ҳ��ʾʱ�����ŷ���ͼ ��,�ŷָ�
	private String simpleContent;
	private String tag;  //#С��ǩ  ����#����¡��  ����#��
	private long itemId;  //��Ӧ��������Ŀid 
	private String itemName;   //������Ŀ����
	private String itemDiscount;  //������Ŀ���Żݼ�
	private String viewCount;   //�ռǲ鿴����
	private int commentCount;   //�ռ�������
	private long diaryTypeId;   //�ռ��������ռǷ���id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getAuthorAvatar() {
		return authorAvatar;
	}
	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getSimpleContent() {
		return simpleContent;
	}
	public void setSimpleContent(String simpleContent) {
		this.simpleContent = simpleContent;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDiscount() {
		return itemDiscount;
	}
	public void setItemDiscount(String itemDiscount) {
		this.itemDiscount = itemDiscount;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public DiaryDTO(long id, String author, Date publishTime, String authorAvatar, String coverImg,
			String simpleContent, String tag, long itemId, String itemName, String itemDiscount, String viewCount,
			int commentCount,long diaryTypeId) {
		super();
		this.id = id;
		this.author = author;
		this.publishTime = publishTime;
		this.authorAvatar = authorAvatar;
		this.coverImg = coverImg;
		this.simpleContent = simpleContent;
		this.tag = tag;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDiscount = itemDiscount;
		this.viewCount = viewCount;
		this.commentCount = commentCount;
		this.diaryTypeId = diaryTypeId;
	}
	public DiaryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
