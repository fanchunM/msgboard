package com.mine.product.szmtr.msgboard.person.dto;

import java.util.Date;

/**
 * @title: LoginHistoryDto
 * @description:
 * @auther: yeaho_lee
 * @version: 1.0
 * @create 2019/9/10 15:02
 */
public class LoginHistoryDto {

    private String id;

    private String userName;

    private String ip;

    private int status;//0:登陆失败，1：登陆成功

    private int ifUse; //0：正常，1：作废


    private Date createTimestamp;

    private Date lastUpdateTimestamp;

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
