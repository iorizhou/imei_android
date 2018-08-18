package com.imei666.android.mvp.model.dto;

public class DiaryTypeDTO {

	private long id;
	private String name;
	private int sortOrder;
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
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public DiaryTypeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DiaryTypeDTO(long id, String name, int sortOrder) {
		super();
		this.id = id;
		this.name = name;
		this.sortOrder = sortOrder;
	}

	
}
