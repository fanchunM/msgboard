package com.mine.product.szmtr.msgboard.message.dto;

import java.io.Serializable;

public class MessageLikeDto implements Serializable{
	private static final long serialVersionUID = 5688267221400069935L;
	
	private String id;
	private String personId;//点赞会员Id
	private String messageId;//点赞关联的留言
	private String createTimestamp;
	private String lastUpdateTimestamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
