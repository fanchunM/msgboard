package com.mine.product.szmtr.msgboard.faqs.dto;

import java.io.Serializable;

import com.mine.product.szmtr.msgboard.faqs.service.IFaqsService.FAQSSTATUS;

public class FaqsDto implements Serializable {

	private static final long serialVersionUID = 8263997331568913091L;

	private String id;
	private String question;		//问题
	private String answers;			//答案
	private Integer orderIndex;		//排序
	private Integer hits;			//点击量
	private FAQSSTATUS faqsStatus;		//审核状态
	private String apprivalPersonId;	//审核人id
	private String personId;		//创建人id
	private String personName;		//创建人名字
	private String apprivalPersonName;//审核人名字
	private String createTimestamp;
	private String lastUpdateTimestamp;
	private String mainText;
	private String themeStr;//关联的主题分类
	
	public String getApprivalPersonName() {
		return apprivalPersonName;
	}
	public void setApprivalPersonName(String apprivalPersonName) {
		this.apprivalPersonName = apprivalPersonName;
	}
	public String getMainText() {
		return mainText;
	}
	public void setMainText(String mainText) {
		this.mainText = mainText;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
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
	public FAQSSTATUS getFaqsStatus() {
		return faqsStatus;
	}
	public void setFaqsStatus(FAQSSTATUS faqsStatus) {
		this.faqsStatus = faqsStatus;
	}
	public String getApprivalPersonId() {
		return apprivalPersonId;
	}
	public void setApprivalPersonId(String apprivalPersonId) {
		this.apprivalPersonId = apprivalPersonId;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getThemeStr() {
		return themeStr;
	}
	public void setThemeStr(String themeStr) {
		this.themeStr = themeStr;
	}
}
