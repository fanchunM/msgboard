package com.mine.product.szmtr.msgboard.person.model;

import java.io.Serializable;
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

/**
 * 
 * @author 何森
 *临时表
 */
@Entity
public class Cursor  implements Serializable{
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	@Column
	private String openId;
	@Column
	private String unionId;//微信用户唯一标识
	@Column
	private String nickName;//微信用户昵称
	@Column
	private String sex;//微信用户性别
	@Column
	private String headImgUrl;//微信用户头像地址
	@Column
	private Date subScribeTime;//微信用户关注时间
	@Column
	private String type;//用户来源
	
	

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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Date getSubScribeTime() {
		return subScribeTime;
	}
	public void setSubScribeTime(Date subScribeTime) {
		this.subScribeTime = subScribeTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	
	
}
