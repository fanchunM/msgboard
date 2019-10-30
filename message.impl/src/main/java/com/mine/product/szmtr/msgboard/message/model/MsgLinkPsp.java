/**
 * 
 */
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
 * 
 * @author 李一豪
 *留言推送公众服务平台数据记录
 */
@Entity
public class MsgLinkPsp {
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	@Column
	private int status;//0:未推送;1:推送成功;2:推送失败
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

	public int getStatus() {
		return status;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
	
}
