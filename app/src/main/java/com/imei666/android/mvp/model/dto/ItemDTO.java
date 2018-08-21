package com.imei666.android.mvp.model.dto;

import java.util.List;

public class ItemDTO {
	private long id;
	private String name;
	private String cover;
	private String doctorName;
	private long doctorId;
	private int jumpType;
	private String jumpUrl;
	private int sortOrder;
	private String detailsUrl;
	private long hospitalId;
	private long typeId;
	private long parentTypeId;
	private long parentParentTypeId;
	private String hospitalCover;
	private String hospitalName;
	private String hospitalAddr;
	private String hospitalWebsite;
	private String hospitalGps;
	private String city;
	private long djCount;
	private long discountPrice;
	private long origPrice;
	
	
	public long getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(long discountPrice) {
		this.discountPrice = discountPrice;
	}
	public long getOrigPrice() {
		return origPrice;
	}
	public void setOrigPrice(long origPrice) {
		this.origPrice = origPrice;
	}
	public long getDjCount() {
		return djCount;
	}
	public void setDjCount(long djCount) {
		this.djCount = djCount;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
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
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getDetailsUrl() {
		return detailsUrl;
	}
	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
	public long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public long getParentTypeId() {
		return parentTypeId;
	}
	public void setParentTypeId(long parentTypeId) {
		this.parentTypeId = parentTypeId;
	}
	public long getParentParentTypeId() {
		return parentParentTypeId;
	}
	public void setParentParentTypeId(long parentParentTypeId) {
		this.parentParentTypeId = parentParentTypeId;
	}
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
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public ItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ItemDTO(long id, String name, String cover, String doctorName, long doctorId, int jumpType, String jumpUrl,
			int sortOrder, String detailsUrl, long hospitalId, long typeId, long parentTypeId, long parentParentTypeId,
			String hospitalCover, String hospitalName, String hospitalAddr, String hospitalWebsite, String hospitalGps,
			String city, long djCount) {
		super();
		this.id = id;
		this.name = name;
		this.cover = cover;
		this.doctorName = doctorName;
		this.doctorId = doctorId;
		this.jumpType = jumpType;
		this.jumpUrl = jumpUrl;
		this.sortOrder = sortOrder;
		this.detailsUrl = detailsUrl;
		this.hospitalId = hospitalId;
		this.typeId = typeId;
		this.parentTypeId = parentTypeId;
		this.parentParentTypeId = parentParentTypeId;
		this.hospitalCover = hospitalCover;
		this.hospitalName = hospitalName;
		this.hospitalAddr = hospitalAddr;
		this.hospitalWebsite = hospitalWebsite;
		this.hospitalGps = hospitalGps;
		this.city = city;
		this.djCount = djCount;
	}
	public String getHospitalCover() {
		return hospitalCover;
	}
	public void setHospitalCover(String hospitalCover) {
		this.hospitalCover = hospitalCover;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getHospitalAddr() {
		return hospitalAddr;
	}
	public void setHospitalAddr(String hospitalAddr) {
		this.hospitalAddr = hospitalAddr;
	}
	public String getHospitalWebsite() {
		return hospitalWebsite;
	}
	public void setHospitalWebsite(String hospitalWebsite) {
		this.hospitalWebsite = hospitalWebsite;
	}
	public String getHospitalGps() {
		return hospitalGps;
	}
	public void setHospitalGps(String hospitalGps) {
		this.hospitalGps = hospitalGps;
	}

	
	
}
