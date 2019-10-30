package com.mine.product.szmtr.msgboard.faqs.model;

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

import com.mine.product.szmtr.msgboard.faqs.service.IFaqsService.FAQSSTATUS;

/**
 * 便民问答
 */
@Entity
public class FaqsModel {
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	@Column
	private String question;		//问题
	@Column(columnDefinition="CLOB") 
	private String answers;			//答案
	@Column
	private int hits;				//点击量
	@Column
	private FAQSSTATUS faqsStatus;	//审核状态
	@Column
	private String apprivalPersonId;//审核人
	@Column
	private String personId;		//创建人
	@Column
	private int orderIndex;			//排序
	
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

	public String getApprivalPersonId() {
		return apprivalPersonId;
	}

	public void setApprivalPersonId(String apprivalPersonId) {
		this.apprivalPersonId = apprivalPersonId;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public FAQSSTATUS getFaqsStatus() {
		return faqsStatus;
	}

	public void setFaqsStatus(FAQSSTATUS faqsStatus) {
		this.faqsStatus = faqsStatus;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
}
