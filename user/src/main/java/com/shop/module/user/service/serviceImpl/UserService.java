package com.shop.module.user.service.serviceImpl;

import com.shop.module.user.dao.*;
import com.shop.module.user.entity.*;
import com.shop.module.user.model.ServiceModel;
import com.shop.module.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    BaseUserMapper baseUserMapper;
    @Autowired
    BuyerUserMapper buyerUserMapper;


    @Override
    public ServiceModel<String> addUser() {
        return null;
    }

    @Override
    public ServiceModel<String> addBuyerUser(BaseUser baseUser) {
        ServiceModel<String> serviceModel=new ServiceModel<>();
        int i=baseUserMapper.insert(baseUser);
        Integer userId=baseUser.getUserId();
        System.out.println(userId);
        if (i>0){
            serviceModel.setSuccess(true);
        }else
            serviceModel.setSuccess(false);
        BuyerUser buyerUser=new BuyerUser();
        buyerUser.setUserId(baseUser.getUserId());
        return serviceModel;
    }

    @Override
    public Boolean accountUse(String account, Integer type) {
        return false;
    }

    @Override
    public List<UserPermission> selectPermissionByUserId(String uid) {
        return permissionDao.selectPermissionByUserId(uid);
    }

    @Override
    public List<UserRole> selectRoleByUserId(String uid) {
        return roleDao.selectRoleByUserId(uid);
    }

    @Override
    public User selectUserById(String uid) {
        User user=  userDao.selectUserById(uid);
        return user;
    }

    @Override
    public List<UserPermission> selectPermissionByType(Integer type) {
        return permissionDao.selectPermissionByType(type);
    }
}
