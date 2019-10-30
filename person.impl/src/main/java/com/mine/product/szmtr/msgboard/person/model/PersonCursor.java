package com.mine.product.szmtr.msgboard.person.model;

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
 * @author 何森
 *用户临时关联表
 */
@Entity
public class PersonCursor {
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
	private String id;
	
	@Column
	private String personId;
	@Column
	private String cursorId;
	
	@Column
	private Date createTimestamp;
	@Column
	private Date lastUpdateTimestamp;
	@Column
    private String type;//关联来源
	@Column
	private String phoneState;//通过苏e行访问，0为手机号不存在，1为手机号被使用过
	@Column
	private String nickNameState;//通过苏e行访问，0为系统随机昵称，1为用户修改后昵称
	@Column
	private String userNameState;//通过苏e行访问，0为系统随机用户名，1为用户修改后
	@Column
    private String passWordState;//通过苏e行访问，0为系统随机密码，1为用户修改后
	@PrePersist
	public void updateWhenCreate() {
		setCreateTimestamp(Calendar.getInstance().getTime());
		setLastUpdateTimestamp(Calendar.getInstance().getTime());
	}

	@PreUpdate
	public void updateWhenUpdate() {
		setLastUpdateTimestamp(Calendar.getInstance().getTime());
	}
	
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
