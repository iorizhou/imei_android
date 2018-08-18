package com.imei666.android.mvp.model.dto;

import java.util.Date;

public class ActivityDTO {
	private long id;
	private String name;
	private String description;
	private Date createTime;
	private int isRecommendToHomepage;
	private String picUrl;
	private String jumpUrl;
	private Date beginTime;
	private Date endTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getIsRecommendToHomepage() {
		return isRecommendToHomepage;
	}
	public void setIsRecommendToHomepage(int isRecommendToHomepage) {
		this.isRecommendToHomepage = isRecommendToHomepage;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getJumpUrl() {
		return jumpUrl;
	}
	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public ActivityDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActivityDTO(Long id,String name, String description, Date createTime, int isRecommendToHomepage, String picUrl,
			String jumpUrl, Date beginTime, Date endTime) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createTime = createTime;
		this.isRecommendToHomepage = isRecommendToHomepage;
		this.picUrl = picUrl;
		this.jumpUrl = jumpUrl;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}
	
	
}
