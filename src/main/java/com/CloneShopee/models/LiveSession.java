package com.CloneShopee.models;

import java.util.Date;

public class LiveSession {
	private Integer id;
	private String title;
	private String content;
	private String image;
	private Date createdDate;
	private Date startDate;
	private Date endDate;
	private Integer countCart;
	private Integer intereaction;
	private Integer duration;
	private Shop shop;
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getCountCart() {
		return countCart;
	}
	public void setCountCart(Integer countCart) {
		this.countCart = countCart;
	}
	public Integer getIntereaction() {
		return intereaction;
	}
	public void setIntereaction(Integer intereaction) {
		this.intereaction = intereaction;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
}
