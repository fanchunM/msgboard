package com.mine.product.szmtr.msgboard.message.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.mine.product.szmtr.msgboard.message.service.IMessageService.APPROVAL_STATUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.DISPLAY_STATUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.ENABLE_STAUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.MESSAGE_TYPE;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.ORDER_STATUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.REPLY_STATUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.VIEW_STAUS;

/**
 * 留言/评论/回复
 */
@Entity
public class Message {
	
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	/**
	 * 父级Id(留言父级ID为空、评论为留言Id、回复为留言Id或者评论Id)
	 */
	@ManyToOne
	private Message parent;			
	/**
	 * 信息类型：留言,评论,回复
	 */
	@Column
	private MESSAGE_TYPE msgType;
	
	
	/**
	 * 留言编号:WEB20180813154133
	 */
	@Column
	private String messageNo;
	/**
	 * 留言类型:数据字典
	 */
	@Column
	private String messageCategory;
	/**
	 * 标题
	 */
	@Column
	private String title;
	/**
	 * 提出人:留言/评论-Person，回复-User
	 */
	@Column
	private String personId;
	/**
	 * IP地址 
	 */
	@Column
	private String ipAddress;
	/**
	 * 内容
	 */
	@Column(columnDefinition="CLOB")
	private String commentStr;
	/**
	 * 处办部门ID
	 */
	@Column
	private String deptId;
	/**
	 * 评分
	 */
	@Column
	private double gradeValue;
	/**
	 * 点赞数
	 */
	@Column
	private long messageLike;
	/**
	 * 审核状态
	 */
	@Column
	private APPROVAL_STATUS approvalStatus;
	/**
	 * 回复状态
	 */
	@Column
	private REPLY_STATUS replyStatus;
	/**
	 * 工单状态
	 */
	@Column
	private ORDER_STATUS orderStatus;
	/**
	 * 显示状态
	 */
	@Column
	private DISPLAY_STATUS displayStatus;
	/**
	 * 是否删除
	 */
	@Column
	private ENABLE_STAUS messageEnable;
	/**
	 * 留言用户手机号
	 */
	@Column
	private String phone;
	/**
	 * 留言推送至公众服务平台状态
	 */
	@Column
	private int status;//0:未推送;1:推送成功;2:推送失败;3:已回复
	@Column
	private VIEW_STAUS  viewStatus;//0：未读取；1：已读取
	@Column
	private Date createTimestamp;
	@Column
	private Date lastUpdateTimestamp;
	@Column
	private String apprivalPersonId;//审核人id
	@Column
	private String serialNumber;//流水号
	/**
	 * 处办部门名字
	 */
	@Column
	private String deptName;
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

	public Message getParent() {
		return parent;
	}

	public void setParent(Message parent) {
		this.parent = parent;
	}

	public MESSAGE_TYPE getMsgType() {
		return msgType;
	}

	public void setMsgType(MESSAGE_TYPE msgType) {
		this.msgType = msgType;
	}

	public String getMessageNo() {
		return messageNo;
	}

	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}

	public String getMessageCategory() {
		return messageCategory;
	}

	public void setMessageCategory(String messageCategory) {
		this.messageCategory = messageCategory;
	}

	

	public VIEW_STAUS getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(VIEW_STAUS viewStatus) {
		this.viewStatus = viewStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public APPROVAL_STATUS getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(APPROVAL_STATUS approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public REPLY_STATUS getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(REPLY_STATUS replyStatus) {
		this.replyStatus = replyStatus;
	}

	public ORDER_STATUS getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ORDER_STATUS orderStatus) {
		this.orderStatus = orderStatus;
	}

	public DISPLAY_STATUS getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(DISPLAY_STATUS displayStatus) {
		this.displayStatus = displayStatus;
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

	public long getMessageLike() {
		return messageLike;
	}

	public void setMessageLike(long messageLike) {
		this.messageLike = messageLike;
	}

	public String getCommentStr() {
		return commentStr;
	}

	public void setCommentStr(String commentStr) {
		this.commentStr = commentStr;
	}

	public double getGradeValue() {
		return gradeValue;
	}

	public void setGradeValue(double gradeValue) {
		this.gradeValue = gradeValue;
	}

	public ENABLE_STAUS getMessageEnable() {
		return messageEnable;
	}

	public void setMessageEnable(ENABLE_STAUS messageEnable) {
		this.messageEnable = messageEnable;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getApprivalPersonId() {
		return apprivalPersonId;
	}

	public void setApprivalPersonId(String apprivalPersonId) {
		this.apprivalPersonId = apprivalPersonId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
