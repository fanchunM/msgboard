package com.mine.product.szmtr.msgboard.person.dto;

import java.io.Serializable;

public class CursorDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -638954613915341105L;
	private String id;
	private String openId;
	private String unionId;//微信用户唯一标识
	private String nickName;//微信用户昵称
	private int sex;//微信用户性别
	private String headImgUrl;//微信用户头像地址
	private String type;//用户来源
	private String createTimestamp;
	private String lastUpdateTimestamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateTimestamp() {
		return createTimestamp;
	}
	public void setCreateTimestamp(String createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	public String getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}
	public void setLastUpdateTimestamp(String lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
	
	
	
}
