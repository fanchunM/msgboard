package com.mine.product.szmtr.msgboard.message.dto;

import java.io.Serializable;


public class ImageDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6487409213340700383L;
	private String id;
	private String address;//图片地址
	private String parentId;//父级Id
	private String imageName;//图片名称
	private String createTimestamp;
	private String lastUpdateTimestamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
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
	
}
