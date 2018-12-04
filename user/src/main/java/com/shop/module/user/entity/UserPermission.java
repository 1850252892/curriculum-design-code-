package com.shop.module.user.entity;

public class UserPermission {
    private Integer id;
    private Integer pid;
    private Integer leave;
    private Integer type;
    private String perName;
    private String descrip;
    private String desCode;
    private String url;
    private Integer enable;
    private Integer sortNum;

    public UserPermission() {
    }

    public UserPermission(Integer id, Integer pid, Integer leave, Integer type, String perName, String descrip, String desCode, String url, Integer enable, Integer sortNum) {
        this.id = id;
        this.pid = pid;
        this.leave = leave;
        this.type = type;
        this.perName = perName;
        this.descrip = descrip;
        this.desCode = desCode;
        this.url = url;
        this.enable = enable;
        this.sortNum = sortNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getDesCode() {
        return desCode;
    }

    public void setDesCode(String desCode) {
        this.desCode = desCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "UserPermission[" +
                "id=" + id +
                ", pid=" + pid +
                ", leave=" + leave +
                ", type=" + type +
                ", perName='" + perName + '\'' +
                ", descrip='" + descrip + '\'' +
                ", desCode='" + desCode + '\'' +
                ", url='" + url + '\'' +
                ", enable=" + enable +
                ", sortNum=" + sortNum +
                ']';
    }
}
