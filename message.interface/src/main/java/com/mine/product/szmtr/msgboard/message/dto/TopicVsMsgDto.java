package com.mine.product.szmtr.msgboard.message.dto;

import java.io.Serializable;
import java.util.Date;

public class TopicVsMsgDto implements Serializable{
	
	private static final long serialVersionUID = 1029393184837875946L;
	
	private String id;
	private String topicId; //话题
	private String messageId;//留言
	private Date createTimestamp;
	private Date lastUpdateTimestamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
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
