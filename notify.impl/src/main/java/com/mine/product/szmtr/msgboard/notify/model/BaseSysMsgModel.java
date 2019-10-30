package com.mine.product.szmtr.msgboard.notify.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BaseSysMsgModel {
    @Id
    private String id;
    private String sendUserId;
    private String receiveUserId;
    private String messageId;
    private Date readTime; //阅读时间
    private Date deleteTime; //删除时间
    private String contentText; //内容
    /**
     * 0：待发送
     * 1：已发送
     * 2：发送失败
     */
    private int appSendStatus;
    private Date createTimestamp;
    private Date lastUpdateTimestamp;
    private String deleteReasonText;//被删除原因
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
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

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getAppSendStatus() {
        return appSendStatus;
    }

    public void setAppSendStatus(int appSendStatus) {
        this.appSendStatus = appSendStatus;
    }

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getDeleteReasonText() {
		return deleteReasonText;
	}

	public void setDeleteReasonText(String deleteReasonText) {
		this.deleteReasonText = deleteReasonText;
	}
}
