package com.mine.product.szmtr.msgboard.message.dto;

import java.io.Serializable;

public class SysDictionaryDto implements Serializable{

	private static final long serialVersionUID = -3294601339255654593L;
	
	private String id;
	private String chsName;//中文名
	private String parentThemeId;//父级主题Id
	private String parentThemeName;//父级主题名称
	private String createTimestamp;//创建时间
	private String lastUpdateTimestamp;//修改时间
	private String serialNumber;//主题序号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getChsName() {
		return chsName;
	}
	public void setChsName(String chsName) {
		this.chsName = chsName;
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
	public String getParentThemeId() {
		return parentThemeId;
	}
	public String getParentThemeName() {
		return parentThemeName;
	}
	public void setParentThemeId(String parentThemeId) {
		this.parentThemeId = parentThemeId;
	}
	public void setParentThemeName(String parentThemeName) {
		this.parentThemeName = parentThemeName;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
