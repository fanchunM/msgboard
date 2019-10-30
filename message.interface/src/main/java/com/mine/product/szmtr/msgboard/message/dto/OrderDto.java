/**
 * 
 */
package com.mine.product.szmtr.msgboard.message.dto;

import java.io.Serializable;

/**
 * @author 39380
 *
 */
public class OrderDto implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 6511783464928316090L;
	private String resourceInfoId; //information resource 信息来源
	 private String orderTypeId; //工单类型
	 private String handSuggDeptId;//处办部门
	 private String mainKeyWordId; //主关键词
	 private String title; //标题
	 private String content; //内容
	 private String exterFeedTimeStr; //反馈时限
	public String getResourceInfoId() {
		return resourceInfoId;
	}
	public String getOrderTypeId() {
		return orderTypeId;
	}
	public String getHandSuggDeptId() {
		return handSuggDeptId;
	}
	public String getMainKeyWordId() {
		return mainKeyWordId;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getExterFeedTimeStr() {
		return exterFeedTimeStr;
	}
	public void setResourceInfoId(String resourceInfoId) {
		this.resourceInfoId = resourceInfoId;
	}
	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	public void setHandSuggDeptId(String handSuggDeptId) {
		this.handSuggDeptId = handSuggDeptId;
	}
	public void setMainKeyWordId(String mainKeyWordId) {
		this.mainKeyWordId = mainKeyWordId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setExterFeedTimeStr(String exterFeedTimeStr) {
		this.exterFeedTimeStr = exterFeedTimeStr;
	}
	 
}
