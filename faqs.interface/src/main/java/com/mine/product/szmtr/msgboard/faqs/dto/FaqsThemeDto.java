package com.mine.product.szmtr.msgboard.faqs.dto;

import java.io.Serializable;
import java.util.Date;

public class FaqsThemeDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8706714310861965643L;
	private String id;
	private String themeId;
	private String faqsId;
	private String themeName;
	private Date createTimestamp;
	private Date lastUpdateTimestamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getThemeId() {
		return themeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	public String getFaqsId() {
		return faqsId;
	}
	public void setFaqsId(String faqsId) {
		this.faqsId = faqsId;
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
	public String getThemeName() {
		return themeName;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	
	
}
