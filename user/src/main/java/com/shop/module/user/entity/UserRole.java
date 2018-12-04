package com.shop.module.user.entity;

public class UserRole {
    private Integer id;
    private String roleName;
    private Integer descrip;

    public UserRole() {
    }

    public UserRole(Integer id, String name, Integer descrip) {
        this.id = id;
        this.roleName = name;
        this.descrip = descrip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String name) {
        this.roleName = name;
    }

    public Integer getDescrip() {
        return descrip;
    }

    public void setDescrip(Integer descrip) {
        this.descrip = descrip;
    }

    @Override
    public String toString() {
        return "UserRole[" +
                "id=" + id +
                ", name='" + roleName + '\'' +
                ", descrip=" + descrip +
                ']';
    }
}
