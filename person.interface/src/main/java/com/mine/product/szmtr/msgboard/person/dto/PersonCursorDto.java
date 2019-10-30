package com.mine.product.szmtr.msgboard.person.dto;

import java.io.Serializable;


public class PersonCursorDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5517697819857047686L;
	private String id;
	private String personId;
	private String cursorId;
	private String createTimestamp;
	private String lastUpdateTimestamp;
    private String type;//关联来源
    private String phoneState;//通过苏e行访问，0为手机号不存在，1为手机号被使用过
    private String nickNameState;//通过苏e行访问，0为系统随机昵称状态，1为用户修改后昵称
    private String userNameState;//通过苏e行访问，0为系统随机用户名状态，1为用户修改后
    private String passWordState;//通过苏e行访问，0为系统随机密码状态，1为用户修改后
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getCursorId() {
		return cursorId;
	}
	public void setCursorId(String cursorId) {
		this.cursorId = cursorId;
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
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getPhoneState()
    {
        return phoneState;
    }
    public void setPhoneState(String phoneState)
    {
        this.phoneState = phoneState;
    }
    public String getNickNameState()
    {
        return nickNameState;
    }
    public void setNickNameState(String nickNameState)
    {
        this.nickNameState = nickNameState;
    }
    public String getUserNameState()
    {
        return userNameState;
    }
    public void setUserNameState(String userNameState)
    {
        this.userNameState = userNameState;
    }
    public String getPassWordState()
    {
        return passWordState;
    }
    public void setPassWordState(String passWordState)
    {
        this.passWordState = passWordState;
    }
}
