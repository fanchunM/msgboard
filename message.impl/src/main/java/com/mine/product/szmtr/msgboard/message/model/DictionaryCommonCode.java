package com.mine.product.szmtr.msgboard.message.model;

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

/**
 * 数据字典（主题内容信息）
 * @author 何森
 *
 */
@Entity
public class DictionaryCommonCode {
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	//父级编码Id
	@Column
	private String parentThemeId;
	
	//中文名
	@Column
	private String chsName;
	
	@Column
	private Date createTimestamp;
	@Column
	private Date lastUpdateTimestamp;
	@Column
	private String serialNumber;//主题序号
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

	public String getChsName() {
		return chsName;
	}

	public void setChsName(String chsName) {
		this.chsName = chsName;
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

	public String getParentThemeId() {
		return parentThemeId;
	}

	public void setParentThemeId(String parentThemeId) {
		this.parentThemeId = parentThemeId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}
