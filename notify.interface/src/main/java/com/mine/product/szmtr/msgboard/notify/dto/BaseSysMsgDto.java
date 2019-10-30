package com.mine.product.szmtr.msgboard.notify.dto;

import java.io.Serializable;
import java.util.Date;

public class BaseSysMsgDto implements Serializable{
	private static final long serialVersionUID = 2309767336031398122L;
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	private String id;
    private String sendUserId;
    private String sendUserStr;
    private String receiveUserId;
    private String receiveUserStr;
    private Date readTimeObj; //阅读时间
    private String readTimeStr;
    private Date deleteTimeObj; //删除时间
    private String deleteTimeStr;
    private String contentText; //内容
    private String messageId;//留言Id
    private String messageTitle;//留言Id
    private String deleteReasonText;//被删除原因
    public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	/**
     * 0：待发送
     * 1：已发送
     * 2：发送失败
     */
    private int apprSendStatus;
    private Date createTimestampObj;
    private String createTimestampStr;
    private Date lastUpdateTimestampObj;
    private String lastUpdateTimestampStr;

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

    public String getSendUserStr() {
        return sendUserStr;
    }

    public void setSendUserStr(String sendUserStr) {
        this.sendUserStr = sendUserStr;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserStr() {
        return receiveUserStr;
    }

    public void setReceiveUserStr(String receiveUserStr) {
        this.receiveUserStr = receiveUserStr;
    }

    public Date getReadTimeObj() {
        return readTimeObj;
    }

    public void setReadTimeObj(Date readTimeObj) {
        this.readTimeObj = readTimeObj;
    }

    public String getReadTimeStr() {
        return readTimeStr;
    }

    public void setReadTimeStr(String readTimeStr) {
        this.readTimeStr = readTimeStr;
    }

    public Date getDeleteTimeObj() {
        return deleteTimeObj;
    }

    public void setDeleteTimeObj(Date deleteTimeObj) {
        this.deleteTimeObj = deleteTimeObj;
    }

    public String getDeleteTimeStr() {
        return deleteTimeStr;
    }

    public void setDeleteTimeStr(String deleteTimeStr) {
        this.deleteTimeStr = deleteTimeStr;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getApprSendStatus() {
        return apprSendStatus;
    }

    public void setApprSendStatus(int apprSendStatus) {
        this.apprSendStatus = apprSendStatus;
    }

    public Date getCreateTimestampObj() {
        return createTimestampObj;
    }

    public void setCreateTimestampObj(Date createTimestampObj) {
        this.createTimestampObj = createTimestampObj;
    }

    public String getCreateTimestampStr() {
        return createTimestampStr;
    }

    public void setCreateTimestampStr(String createTimestampStr) {
        this.createTimestampStr = createTimestampStr;
    }

    public Date getLastUpdateTimestampObj() {
        return lastUpdateTimestampObj;
    }

    public void setLastUpdateTimestampObj(Date lastUpdateTimestampObj) {
        this.lastUpdateTimestampObj = lastUpdateTimestampObj;
    }

    public String getLastUpdateTimestampStr() {
        return lastUpdateTimestampStr;
    }

    public void setLastUpdateTimestampStr(String lastUpdateTimestampStr) {
        this.lastUpdateTimestampStr = lastUpdateTimestampStr;
    }

	public String getDeleteReasonText() {
		return deleteReasonText;
	}

	public void setDeleteReasonText(String deleteReasonText) {
		this.deleteReasonText = deleteReasonText;
	}
    
}