package com.shop.module.user.dao;

import com.shop.module.user.entity.UserRole;

import java.util.List;
import java.util.Map;

public interface RoleDao {
    List<UserRole> selectRoleByUserId(String uid);

    Integer insertRole(UserRole role);

    Integer insertUr(Map<String, Integer> map);
}
