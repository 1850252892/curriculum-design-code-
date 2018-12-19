package com.shop.module.user.service;

import com.shop.module.user.entity.BaseUser;
import com.shop.module.user.entity.User;
import com.shop.module.user.entity.UserPermission;
import com.shop.module.user.entity.UserRole;
import com.shop.module.user.model.ServiceModel;

import javax.xml.ws.Service;
import java.util.List;

public interface IUserService {

    ServiceModel<String> addUser();

    //新建买家用户
    ServiceModel<Integer> addBuyerUser(BaseUser baseUser);

    /**
     * 账号是否已使用
     * @param account 注册用的账号
     * @param type 账号类型（0手机，1邮箱）
     * @return
     */
    Boolean accountUse(String account,Integer type);

    BaseUser selectBaseUser(BaseUser baseUser);

    BaseUser selectBaseUserByAccountMapping(String account);

    ServiceModel updateBaseUser(BaseUser baseUser);

    List<UserPermission> selectPermissionByUserId(String uid);

    List<UserRole> selectRoleByUserId(String uid);

    User selectUserById(String uid);

    List<UserPermission> selectPermissionByType(Integer type);
}
