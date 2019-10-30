package com.mine.product.szmtr.msgboard.news.dto;

import java.io.Serializable;
import java.util.List;

import com.mine.product.szmtr.msgboard.message.dto.ImageDto;
import com.mine.product.szmtr.msgboard.news.service.INewsService.NEWS_STATUS;

public class NewsDto implements Serializable {
	
	private static final long serialVersionUID = -8049808874574338880L;
	
	private String id;
	private String title;		//新闻标题
	private String subTitle;	//新闻副标题
	private String keyword;		//关键词
	private String author;		//作者
	private String deptName;	//部门
	private boolean topStatus;		//栏目置顶
	private String publishTime;	//发布日期
	private String newsContent;	//新闻内容
	private String newsDesc;	//新闻介绍
	private int hits;			//点击量
	private NEWS_STATUS newsStatus;	//审核状态
	private String personId;	//创建人
	private String apprivalPersonId;	//审核人id
	private String personName;  //创建人姓名
	private String apprivalPersonName;	//审核人姓名
	private String linkURL;	//外链
	private List<ImageDto> newsImageList;
	private String frontUrl;	//配置文件路径(查看新闻详情)
	private String createTimestamp;
	private String lastUpdateTimestamp;
	
	
	
	public String getFrontUrl() {
		return frontUrl;
	}
	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}
	public String getApprivalPersonId() {
		return apprivalPersonId;
	}
	public void setApprivalPersonId(String apprivalPersonId) {
		this.apprivalPersonId = apprivalPersonId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getApprivalPersonName() {
		return apprivalPersonName;
	}
	public void setApprivalPersonName(String apprivalPersonName) {
		this.apprivalPersonName = apprivalPersonName;
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	public boolean isTopStatus() {
		return topStatus;
	}
	public void setTopStatus(boolean topStatus) {
		this.topStatus = topStatus;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
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
	public String getLinkURL() {
		return linkURL;
	}
	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
	public List<ImageDto> getNewsImageList() {
		return newsImageList;
	}
	public void setNewsImageList(List<ImageDto> newsImageList) {
		this.newsImageList = newsImageList;
	}
	
}
