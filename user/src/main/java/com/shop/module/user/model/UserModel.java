package com.shop.module.user.model;

import com.shop.module.user.entity.BaseUser;

public class UserModel extends BaseUser {
    String verCode;
    Integer regType;

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public Integer getRegType() {
        return regType;
    }

    public void setRegType(Integer regType) {
        this.regType = regType;
    }
}
