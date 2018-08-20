package com.imei666.android.mvp.model.dto;

import java.util.Date;

public class RecommendItemDTO {
	private long id;
	private long itemId;
	private int recommend;  //0不推荐 1推荐
	private String createTime;
	private String beginTime;
	private String endTime;
	private long activityId;   //对应的活动ID
	private String picUrl;
	private long discount;
	private long origPrice;
	private String name;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public long getOrigPrice() {
		return origPrice;
	}

	public void setOrigPrice(long origPrice) {
		this.origPrice = origPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RecommendItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RecommendItemDTO(long id,long itemId, int recommend, String createTime, String beginTime, String endTime,long activityId) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.recommend = recommend;
		this.createTime = createTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.activityId = activityId;
	}
	
	
}
