package com.shop.module.user.controller;

import com.alibaba.fastjson.JSON;
import com.shop.module.user.model.ResponseModel;
import com.shop.module.user.service.ITpUserService;
import com.shop.module.user.service.IUserService;
import com.shop.module.user.util.MapEnum;
import com.shop.module.user.util.QQLogin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TPLoginController {

    @Value("${shop.qqLogin.getOpenIdUrl}")
    String QQ_GET_OPENID_URL;
    @Value("${shop.qqLogin.getUerInfo}")
    String QQ_GET_USER_INFO;
    @Autowired
    QQLogin qqLogin;
    @Autowired
    IUserService userService;
    @Autowired
    ITpUserService iTpUserService;
    @GetMapping("qq")
    public ResponseModel getQQLoginUrl(){
        ResponseModel responseModel=new ResponseModel();
        responseModel.setCode(200);
        Map<String,String> map=new HashMap<>();
        map.put("url", qqLogin.getRequestUrl());
        responseModel.setData(map);
        return responseModel;
    }
    @SuppressWarnings("unchecked")
    @GetMapping("/egCallBack")
    public String qqCallBack( @RequestParam("access_token") String access_token, @RequestParam("expires_in")String expires_in){
        ResponseModel responseModel=ResponseModel.newResponseModel();
        String openId=qqLogin.getOpenId(access_token);
        if (openId.equals("e")){
            //获取openId发生错误
            responseModel.setCode(400);
            return JSON.toJSONString(responseModel);
        }
        Boolean isExists=iTpUserService.openIdIsExists(openId);
        if (!isExists){
            //该qq是首次登录,在本地数据库存储用户信息
            Map<String,String> userInfo= MapEnum.HashMap.createMap();
            userInfo=qqLogin.getUserInfo(access_token,openId);
            if (userInfo.containsKey("e")){
                //获取userInfo失败
            }
            String nickname=userInfo.get("nickname");
            String userLogo=userInfo.get("figureurl_2");
            iTpUserService.saveTpUser(openId,nickname,userLogo);
        }
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(openId,new Md5Hash(openId).toString());

        Map<String,Object> map=MapEnum.HashMap.crateObjectMap();
        try {
            subject.login(token);
            map.put("token",subject.getSession().getId());
            map.put("openId",openId);
            responseModel.setData(map);
            responseModel.setCode(200);
            System.out.println("登录成功");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
        } catch (LockedAccountException e) {
            System.out.println("登录失败，账号已锁定");
        } catch (AuthenticationException e) {
            System.out.println("该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(responseModel);
    }


}
