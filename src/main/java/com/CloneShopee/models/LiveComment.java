package com.CloneShopee.models;

public class LiveComment {
	private Integer id;
	private LiveSession live;
	private String content;
	private Integer commnetId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LiveSession getLive() {
		return live;
	}
	public void setLive(LiveSession live) {
		this.live = live;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCommnetId() {
		return commnetId;
	}
	public void setCommnetId(Integer commnetId) {
		this.commnetId = commnetId;
	}
	
}
