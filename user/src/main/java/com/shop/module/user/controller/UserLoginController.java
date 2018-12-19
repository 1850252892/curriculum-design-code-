package com.shop.module.user.controller;

import com.shop.module.user.entity.BaseUser;
import com.shop.module.user.model.ResponseModel;
import com.shop.module.user.model.UserModel;
import com.shop.module.user.service.IUserService;
import com.shop.module.user.util.MapEnum;
import com.shop.module.user.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class UserLoginController extends ResponseModel {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    IUserService userService;
    @PostMapping("userLogin")
    public ResponseModel userLogin(@RequestBody UserModel userModel){
        ResponseModel responseModel=newResponseModel();
        String account=userModel.getAccount();
        String password=userModel.getPassword();
        String verCode=userModel.getVerCode();
        if (userModel.getVerCode()!=null){
            String redisKey=UserRegController.VERCODE+userModel.getAccount();
            if (!redisUtil.exists(redisKey)){
                responseModel.setCode(400);
                responseModel.setMsg("验证码错误或已过期，请重新获取验证码!!!");
                return responseModel;
            }
            String realVerCode= redisUtil.getObject(redisKey) +"";
            if (realVerCode==null||realVerCode.equals("")||!realVerCode.equals(verCode)){
                responseModel.setCode(400);
                responseModel.setMsg("验证码错误或已过期，请重新获取验证码!!!");
                return responseModel;
            }

            BaseUser baseUser=userService.selectBaseUserByAccountMapping(account);


        }else {

        }

        return responseModel;
    }

    public Map<String,Object> getLoginToken(UsernamePasswordToken token){
        Subject subject= SecurityUtils.getSubject();
        Map<String,Object> map= MapEnum.HashMap.crateObjectMap();
        try {
            subject.login(token);
            map.put("token",subject.getSession().getId());
            System.out.println("登录成功");
        } catch (IncorrectCredentialsException e) {
            map.put("error","密码错误");
//            System.out.println("密码错误");
        } catch (LockedAccountException e) {
            map.put("error","账号已锁定");
//            System.out.println("登录失败，账号已锁定");
        } catch (AuthenticationException e) {
            map.put("error","该用户不存在");
//            System.out.println("该用户不存在");
        } catch (Exception e) {
            map.put("error","登陆失败");
            e.printStackTrace();
        }
        return map;
    }


}
