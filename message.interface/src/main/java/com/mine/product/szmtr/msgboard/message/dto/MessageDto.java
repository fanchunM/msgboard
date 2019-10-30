package com.mine.product.szmtr.msgboard.message.dto;

import java.io.Serializable;
import java.util.List;

import com.mine.product.szmtr.msgboard.message.service.IMessageService.APPROVAL_STATUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.DISPLAY_STATUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.ENABLE_STAUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.MESSAGE_TYPE;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.ORDER_STATUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.REPLY_STATUS;
import com.mine.product.szmtr.msgboard.message.service.IMessageService.VIEW_STAUS;

public class MessageDto implements Serializable {
	
	private static final long serialVersionUID = -6936433830529999558L;
	
	private String id;
	private String parentId; //父级Id			
	private MESSAGE_TYPE msgType; //信息类型：留言,评论,回复
	private String messageNo;//留言编号
	private String messageCategory;//留言类型:数据字典 id
	private String messageCategoryName;//留言类型:数据字典 名称
	private String title;//标题
	private String personId;//提出人
	private String personName;//提出人姓名
	private String ipAddress;//IP地址
	private String commentStr;//内容
	private String deptId;//处办部门ID
	private double gradeValue;//评分
	private long messageLike;//点赞数
	private long commentNumber;//评论数
	private long commentNeedNumber;//待审核评论数
	private String validateCode;//验证码
	private String phone;
	private String status;//推送状态
	private APPROVAL_STATUS approvalStatus;//审核状态
	private REPLY_STATUS replyStatus;//回复状态
	private ORDER_STATUS orderStatus;//工单状态
	private DISPLAY_STATUS displayStatus;//显示状态
	private ENABLE_STAUS messageEnable;//是否删除
	private VIEW_STAUS  viewStatus;//是否查看
	private String createTimestamp;
	private String lastUpdateTimestamp;
	private String reply;
	private MessageDto replyDto;
	private List<MessageDto> comments;
	private String mainText;//主题分类name
	private String mainTextId;//主题分类Id
	private String messageTypeName;//留言类型名字
	private String apprivalPersonName;//审核人名字
	private String apprivalPersonId;	//审核人id
	private String serialNumber;//流水号
	private String approvalStatusStr;//审核状态
	private String replyStatusStr;//答复状态
	private String deptName;//处办部门名字
	private long floorNum;//评论楼层号
	private String themeStr;
	private String parentTitle;//父级标题(评论用)
	private String parentcommentStr;//父级内容(评论用)
	private String headPortraits;//留言用户的头像
	private boolean ifHasAttach;//是否含有附件
	public String getMainTextId() {
		return mainTextId;
	}
	public void setMainTextId(String mainTextId) {
		this.mainTextId = mainTextId;
	}
	public String getMainText() {
		return mainText;
	}
	public void setMainText(String mainText) {
		this.mainText = mainText;
	}
	
	public String getMessageTypeName() {
		return messageTypeName;
	}
	public void setMessageTypeName(String messageTypeName) {
		this.messageTypeName = messageTypeName;
	}
	public VIEW_STAUS getViewStatus() {
		return viewStatus;
	}
	public void setViewStatus(VIEW_STAUS viewStatus) {
		this.viewStatus = viewStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	public String getCommentStr() {
		return commentStr;
	}
	public void setCommentStr(String commentStr) {
		this.commentStr = commentStr;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public double getGradeValue() {
		return gradeValue;
	}
	public void setGradeValue(double gradeValue) {
		this.gradeValue = gradeValue;
	}
	public long getMessageLike() {
		return messageLike;
	}
	public void setMessageLike(long messageLike) {
		this.messageLike = messageLike;
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
	public ENABLE_STAUS getMessageEnable() {
		return messageEnable;
	}
	public void setMessageEnable(ENABLE_STAUS messageEnable) {
		this.messageEnable = messageEnable;
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
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public MessageDto getReplyDto() {
		return replyDto;
	}
	public void setReplyDto(MessageDto replyDto) {
		this.replyDto = replyDto;
	}
	public List<MessageDto> getComments() {
		return comments;
	}
	public void setComments(List<MessageDto> comments) {
		this.comments = comments;
	}
	public long getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(long commentNumber) {
		this.commentNumber = commentNumber;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMessageCategoryName() {
		return messageCategoryName;
	}
	public void setMessageCategoryName(String messageCategoryName) {
		this.messageCategoryName = messageCategoryName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getCommentNeedNumber() {
		return commentNeedNumber;
	}
	public void setCommentNeedNumber(long commentNeedNumber) {
		this.commentNeedNumber = commentNeedNumber;
	}
	public String getApprivalPersonName() {
		return apprivalPersonName;
	}
	public void setApprivalPersonName(String apprivalPersonName) {
		this.apprivalPersonName = apprivalPersonName;
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
	public String getApprovalStatusStr() {
		return approvalStatusStr;
	}
	public String getReplyStatusStr() {
		return replyStatusStr;
	}
	public void setApprovalStatusStr(String approvalStatusStr) {
		this.approvalStatusStr = approvalStatusStr;
	}
	public void setReplyStatusStr(String replyStatusStr) {
		this.replyStatusStr = replyStatusStr;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public long getFloorNum() {
		return floorNum;
	}
	public void setFloorNum(long floorNum) {
		this.floorNum = floorNum;
	}
	public String getThemeStr() {
		return themeStr;
	}
	public void setThemeStr(String themeStr) {
		this.themeStr = themeStr;
	}
	public String getParentTitle() {
		return parentTitle;
	}
	public String getParentcommentStr() {
		return parentcommentStr;
	}
	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}
	public void setParentcommentStr(String parentcommentStr) {
		this.parentcommentStr = parentcommentStr;
	}
	public String getHeadPortraits() {
		return headPortraits;
	}
	public void setHeadPortraits(String headPortraits) {
		this.headPortraits = headPortraits;
	}
	public boolean isIfHasAttach() {
		return ifHasAttach;
	}
	public void setIfHasAttach(boolean ifHasAttach) {
		this.ifHasAttach = ifHasAttach;
	}
}
