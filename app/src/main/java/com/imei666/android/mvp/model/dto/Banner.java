package com.imei666.android.mvp.model.dto;

import java.io.Serializable;
import java.util.Date;

public class Banner implements Serializable {
    private long id;
    private String name;
    private String description;
    private int jumpType;
    private String jumpUrl;
    private Date createTime;
    private Date recommendTime;
    private int isRecommend;
    private String picUrl;
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
    public int getJumpType() {
        return jumpType;
    }
    public void setJumpType(int jumpType) {
        this.jumpType = jumpType;
    }
    public String getJumpUrl() {
        return jumpUrl;
    }
    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getRecommendTime() {
        return recommendTime;
    }
    public void setRecommendTime(Date recommendTime) {
        this.recommendTime = recommendTime;
    }
    public int getIsRecommend() {
        return isRecommend;
    }
    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }
    public String getPicUrl() {
        return picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public Banner(long id, String name, String description, int jumpType, String jumpUrl, Date createTime,
                  Date recommendTime, int isRecommend, String picUrl) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.jumpType = jumpType;
        this.jumpUrl = jumpUrl;
        this.createTime = createTime;
        this.recommendTime = recommendTime;
        this.isRecommend = isRecommend;
        this.picUrl = picUrl;
    }
    public Banner() {
        super();
        // TODO Auto-generated constructor stub
    }
}
