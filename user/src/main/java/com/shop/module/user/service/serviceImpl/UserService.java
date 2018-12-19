package com.shop.module.user.service.serviceImpl;

import com.shop.module.user.dao.*;
import com.shop.module.user.entity.*;
import com.shop.module.user.model.ServiceModel;
import com.shop.module.user.service.IUserService;
import com.shop.module.user.util.DateUtil;
import com.shop.module.user.util.UserState;
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
    @Autowired
    TpUserMapper tpUserMapper;


    @Override
    public ServiceModel<String> addUser() {
        return null;
    }

    @Override
    public ServiceModel<Integer> addBuyerUser(BaseUser baseUser) {
        ServiceModel<Integer> serviceModel=new ServiceModel<>();
        baseUser.setStatus(UserState.Init.getValue());//账号状态
        baseUser.setCreateTime(DateUtil.newNowDate());//注册日期
        int i=baseUserMapper.insertSelective(baseUser);
        Integer userId=baseUser.getUserId();
        System.out.println(userId);
        if (i>0){
            serviceModel.setSuccess(true);
            serviceModel.setData(userId);
        }else
            serviceModel.setSuccess(false);
        BuyerUser buyerUser=new BuyerUser();
        buyerUser.setUserId(baseUser.getUserId());
        buyerUser.setIntegral(0);
        buyerUser.setLevelId(1);
        buyerUser.setStatus(UserState.Init.getValue());
        buyerUser.setCreateTime(DateUtil.newNowDate());
        buyerUserMapper.insertSelective(buyerUser);
        return serviceModel;
    }

    @Override
    public Boolean accountUse(String account, Integer type) {
        return false;
    }

    @Override
    public BaseUser selectBaseUser(BaseUser baseUser) {
        return baseUserMapper.selectBaseUserBySelective(baseUser);
    }

    @Override
    public BaseUser selectBaseUserByAccountMapping(String account) {
        return baseUserMapper.selectBaseUserByAccountMapping(account);
    }

    @Override
    public ServiceModel updateBaseUser(BaseUser baseUser) {
        baseUserMapper.updateByPrimaryKeySelective(baseUser);
        return null;
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
