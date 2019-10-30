package com.mine.product.szmtr.msgboard.news.model;

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

import com.mine.product.szmtr.msgboard.news.service.INewsService.NEWS_STATUS;

/**
 * 新闻
 */
@Entity
public class News {
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	@Column
	private String title;		//新闻标题
	@Column
	private String subTitle;	//新闻副标题
	@Column
	private String keyword;		//关键词
	@Column
	private String author;		//作者
	@Column
	private String deptName;	//部门
	@Column
	private boolean topStatus;		//栏目置顶
	@Column
	private Date publishTime;	//发布日期
	@Column(columnDefinition="CLOB") 
	private String newsContent;	//新闻内容
	@Column
	private String newsDesc;	//新闻介绍
	@Column
	private int hits;			//点击量
	@Column
	private NEWS_STATUS newsStatus;	//审核状态
	@Column
	private String personId;	//创建人
	@Column
	private String apprivalPersonId;	//审核人
	
	@Column
	private String linkURL;	//外链
	
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
	
	
	
	
	public String getApprivalPersonId() {
		return apprivalPersonId;
	}

	public void setApprivalPersonId(String apprivalPersonId) {
		this.apprivalPersonId = apprivalPersonId;
	}

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
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	public String getNewsDesc() {
		return newsDesc;
	}
	public void setNewsDesc(String newsDesc) {
		this.newsDesc = newsDesc;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public NEWS_STATUS getNewsStatus() {
		return newsStatus;
	}
	public void setNewsStatus(NEWS_STATUS newsStatus) {
		this.newsStatus = newsStatus;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isTopStatus() {
		return topStatus;
	}

	public void setTopStatus(boolean topStatus) {
		this.topStatus = topStatus;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
}
