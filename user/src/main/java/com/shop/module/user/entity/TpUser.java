package com.shop.module.user.entity;

import java.util.Date;

public class TpUser {
    private String openId;

    private Integer userId;

    private Integer isregist;

    private Integer status;

    private Integer type;

    private Date createTime;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsregist() {
        return isregist;
    }

    public void setIsregist(Integer isregist) {
        this.isregist = isregist;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}