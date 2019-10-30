package com.mine.product.szmtr.msgboard.person.dto;

import java.io.Serializable;

public class UserLoginDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3612716872457589080L;
	private String id;
	private String userName;
	private String password;
	private String weixinId;	//微信号
	private String phone;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWeixinId() {
		return weixinId;
	}
	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
