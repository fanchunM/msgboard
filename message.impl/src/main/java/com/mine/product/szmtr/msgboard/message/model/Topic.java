package com.mine.product.szmtr.msgboard.message.model;

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
 * 话题
 */
@Entity
public class Topic {
	
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	
	/**
	 * 话题标题
	 */
	@Column
	private String title;
	/**
	 * 话题描述
	 */
	@Column
	private String descStr;
	/**
	 * 话题创建者
	 */
	@Column
	private String userId;
	/**
	 * 是不是热门话题
	 */
	@Column
	private boolean hotStatus;
	/**
	 * 排序
	 */
	@Column
	private int orderIndex;
	
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

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDescStr() {
		return descStr;
	}

	public void setDescStr(String descStr) {
		this.descStr = descStr;
	}

	public boolean isHotStatus() {
		return hotStatus;
	}

	public void setHotStatus(boolean hotStatus) {
		this.hotStatus = hotStatus;
	}
}
