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

/**
 * 
 * @author 何森
 *用户管理（黑名单）
 */
@Entity
public class UserManage {

	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	
	@Column
	private String userId;

	@Column
	private Date createTimestamp;
	@Column
	private Date lastUpdateTimestamp;
	
	
	@PrePersist
	public void updateWhenCreate() {
		createTimestamp = Calendar.getInstance().getTime();
		lastUpdateTimestamp = Calendar.getInstance().getTime();
	}
	
	@PreUpdate
	public void updateWhenUpdate() {
		lastUpdateTimestamp = Calendar.getInstance().getTime();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
