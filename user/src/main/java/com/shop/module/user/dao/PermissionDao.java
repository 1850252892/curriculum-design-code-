package com.shop.module.user.dao;

import com.shop.module.user.entity.UserPermission;

import java.util.List;
import java.util.Map;

public interface PermissionDao {
    List<UserPermission> selectPermissionByUserId(String uid);

    List<UserPermission> selectPermissionByType(Integer type);

    Integer insertPermission(UserPermission permission);

    Integer insertRp(Map<String, Integer> map);

}
