package com.shop.module.user.shiroConfig;

import com.shop.module.user.entity.BaseUser;
import com.shop.module.user.entity.User;
import com.shop.module.user.entity.UserPermission;
import com.shop.module.user.entity.UserRole;
import com.shop.module.user.service.IUserService;
import com.shop.module.user.util.DateUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    public static final Logger log= LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    IUserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        Object token = auth.getPrincipal();
        // 解密获得username，用于和数据库进行对比
        String username = String.valueOf(token);
//        User user = userService.selectUserById(username);
        BaseUser selective=new BaseUser();
        selective.setAccount(username);
        BaseUser user=userService.selectBaseUser(selective);
        if (user==null){
            return null;
        }
        user.setLastLoginTime(DateUtil.newNowDate());//更新用户最后登录时间
        userService.updateBaseUser(user);
        String password=user.getPassword();
        log.info("userInfo",user.toString());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
              username, //用户名
                password, //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }

    /**
     * 提供用户信息返回权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        Object user1=  principals.getPrimaryPrincipal();
//        User user=new User();
        String username = principals.toString();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 根据用户名查询当前用户拥有的角色
        List<UserRole> roles = userService.selectRoleByUserId(username);
        Set<String> roleNames = new HashSet<String>();
        for (UserRole role : roles) {
            roleNames.add(role.getRoleName());
        }
        // 将角色名称提供给info
        authorizationInfo.setRoles(roleNames);
        // 根据用户名查询当前用户权限
        List<UserPermission> permissions = userService.selectPermissionByUserId(username);
        Set<String> permissionNames = new HashSet<String>();
        for (UserPermission permission : permissions) {
            permissionNames.add(permission.getPerName());
        }
        // 将权限名称提供给info
        authorizationInfo.setStringPermissions(permissionNames);
        return authorizationInfo;
    }
}
