package com.mine.product.szmtr.msgboard.message.dto;

import java.io.Serializable;
import java.util.Date;

public class TopicDto implements Serializable {
	
	private static final long serialVersionUID = -8814278877110298836L;
	
	private String id;
	private String title;//话题标题
	private String descStr;//话题描述
	private String userId;//话题创建者
	private boolean hotStatus;//是不是热门话题
	private int orderIndex;//排序
	private Date createTimestamp;
	private Date lastUpdateTimestamp;
	
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
	public String getDescStr() {
		return descStr;
	}
	public void setDescStr(String descStr) {
		this.descStr = descStr;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isHotStatus() {
		return hotStatus;
	}
	public void setHotStatus(boolean hotStatus) {
		this.hotStatus = hotStatus;
	}
	public int getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
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
