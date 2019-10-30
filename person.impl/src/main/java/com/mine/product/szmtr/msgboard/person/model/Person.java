package com.mine.product.szmtr.msgboard.person.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.mine.product.szmtr.msgboard.person.service.IPersonService.GENDER;
import com.mine.product.szmtr.msgboard.person.service.IPersonService.PERSONSTATUS;
/**
 * 会员 
 */
@Entity
public class Person {
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	@Column
	private String userName;
	@Column
	private String password;
	@Column
	private String weixinId;	//微信号
	@Column
	private String phone;		//手机号
	@Column
	private GENDER gender;		//性别
	@Column
	private String nickName;	//昵称
	@Column
	private String validateCode;	//验证码
	@Column
	private PERSONSTATUS personStatus;	//启用状态
	@Column
	private String registeredIP;	//注册账号IP地址
	@Column
	private String recentLoginIP;	//最近登录IP地址
	@Column
	private String headPortraits;	//头像（存储的是静态图片文件名）
//	@Column(columnDefinition="BLOB")
//	private String uploadHeadImage;//头像（用户自定义上传头像）
	@Column
	private Date createTimestamp;
	@Column
	private Date lastUpdateTimestamp;
	
	@PrePersist
	public void updateWhenCreate() {
		setCreateTimestamp(Calendar.getInstance().getTime());
		setLastUpdateTimestamp(Calendar.getInstance().getTime());
	}

	@PreUpdate
	public void updateWhenUpdate() {
		setLastUpdateTimestamp(Calendar.getInstance().getTime());
	}
	
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
	public GENDER getGender() {
		return gender;
	}
	public void setGender(GENDER gender) {
		this.gender = gender;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public PERSONSTATUS getPersonStatus() {
		return personStatus;
	}

	public void setPersonStatus(PERSONSTATUS personStatus) {
		this.personStatus = personStatus;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
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

//    public String getUploadHeadImage()
//    {
//        return uploadHeadImage;
//    }
//
//    public void setUploadHeadImage(String uploadHeadImage)
//    {
//        this.uploadHeadImage = uploadHeadImage;
//    }

}
