package com.mine.product.szmtr.msgboard.person.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @title: LoginHistoryModel
 * @description:
 * @auther: yeaho_lee
 * @version: 1.0
 * @create 2019/9/10 14:51
 */
@Entity
public class LoginHistoryModel {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "uuid", parameters = { @Parameter(name = "separator", value = "_") })
    private String id;
    @Column
    private String userName;

    @Column
    private String ip;

    @Column
    private int status;//0:登陆失败，1：登陆成功

    @Column
    private int ifUse; //0：正常，1：作废

    @Column
    private Date createTimestamp;
    @Column
    private Date lastUpdateTimestamp;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getIfUse() {
        return ifUse;
    }

    public void setIfUse(int ifUse) {
        this.ifUse = ifUse;
    }
}
