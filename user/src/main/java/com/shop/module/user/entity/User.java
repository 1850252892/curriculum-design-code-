package com.shop.module.user.entity;

public class User {
    private Integer id;
    private String account;
    private String pwd;
    private String salt;
    private String nickname;
    private String  createTime;
    private Integer isDel;
    private Integer isLock;
    private String lockTime;
    private String email;
    private String phone;
    private Integer  remark;

    public User(Integer id, String account, String pwd, String salt, String nickname, String createTime, Integer isDel, Integer isLock, String lockTime, String email, String phone, Integer remark) {
        this.id = id;
        this.account = account;
        this.pwd = pwd;
        this.salt = salt;
        this.nickname = nickname;
        this.createTime = createTime;
        this.isDel = isDel;
        this.isLock = isLock;
        this.lockTime = lockTime;
        this.email = email;
        this.phone = phone;
        this.remark = remark;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRemark() {
        return remark;
    }

    public void setRemark(Integer remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "User[" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", salt='" + salt + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isDel=" + isDel +
                ", isLock=" + isLock +
                ", lockTime='" + lockTime + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", remark=" + remark +
                ']';
    }
}
