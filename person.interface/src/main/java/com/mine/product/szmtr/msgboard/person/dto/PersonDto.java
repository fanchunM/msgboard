package com.mine.product.szmtr.msgboard.person.dto;

import java.io.Serializable;

public class PersonDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9179110392214524392L;
	private String id;
	private String userName;
	private String password;
	private String weixinId;	//微信号
	private String phone;		//手机号
	private String gender;		//性别
	private String nickName;	//昵称
	private String validateCode;	//验证码
	private String personStatus;	//启用状态
	private long messageNum;//留言数量
	private String registeredIP;	//注册IP
	private String recentLoginIP;	//最近登录IP
	private String headPortraits;	//头像（存储的是静态图片文件名）
	private String uploadHeadImage;//头像（用户自定义上传头像）
	private String createTimestamp;
	private String lastUpdateTimestamp;
	private String sueXingId;//苏e行Id
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPersonStatus() {
		return personStatus;
	}
	public void setPersonStatus(String personStatus) {
		this.personStatus = personStatus;
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
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public long getMessageNum() {
		return messageNum;
	}
	public void setMessageNum(long messageNum) {
		this.messageNum = messageNum;
	}
	public String getRegisteredIP() {
		return registeredIP;
	}
	public void setRegisteredIP(String registeredIP) {
		this.registeredIP = registeredIP;
	}
	public String getRecentLoginIP() {
		return recentLoginIP;
	}
	public void setRecentLoginIP(String recentLoginIP) {
		this.recentLoginIP = recentLoginIP;
	}
	public String getHeadPortraits() {
		return headPortraits;
	}
	public void setHeadPortraits(String headPortraits) {
		this.headPortraits = headPortraits;
	}
    public String getUploadHeadImage()
    {
        return uploadHeadImage;
    }
    public void setUploadHeadImage(String uploadHeadImage)
    {
        this.uploadHeadImage = uploadHeadImage;
    }
    public String getSueXingId()
    {
        return sueXingId;
    }
    public void setSueXingId(String sueXingId)
    {
        this.sueXingId = sueXingId;
    }
	
}
