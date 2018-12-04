package com.shop.module.user.controller;


import com.alibaba.fastjson.JSON;
import com.shop.module.user.entity.User;
import com.shop.module.user.model.ResponseModel;
import com.shop.module.user.service.serviceImpl.UserService;
import com.shop.module.user.util.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/11.
 */
@Controller
public class                                                                                                                                                                                                                                         ShiroController {
    public static final Logger log= LoggerFactory.getLogger(ShiroController.class);
    @Autowired
    UserService userService;
//    @Autowired
//    RedisUtil redisUtil;
    /**
     * 登录方法
     * @param
     * @return
     */
    @RequestMapping(value = "/ajaxLogin")
    @ResponseBody
    public void ajaxLogin() {
//        ResponseModel responseModel=new ResponseModel();
//        String username=user.getAccount();
//        String password=user.getPwd();
//        if (username==null||password==null){
//
//        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("1008611","123456");
        Map<String,String> result=new HashMap<>();
        try {
            subject.login(token);
            System.out.println("登录成功");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
        } catch (LockedAccountException e) {
            System.out.println("登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            System.out.println("该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("is role:"+subject.hasRole("系统管理员"));
        throw new UnauthorizedException();

    }

    @PostMapping(value = "/jwtLogin")
    public @ResponseBody
    String jwtLogin(@RequestBody Map<String,String> map){
        String username=map.get("account");
        String secret=map.get("secret");
        String rememberMe=map.get("rememberMe");
        String password=new Md5Hash(secret,username,1).toString();
        User user=userService.selectUserById(username);
        ResponseModel responseModel=new ResponseModel();
        if (user==null){
            log.info("【登录失败】-用户账号：{}不存在",username);
            responseModel.setCode(ResponseModel.NOTEXISTS);
            responseModel.setMsg("用户名不存在");
            return JSON.toJSONString(responseModel);
        }
        if (user.getIsLock()==1){
            log.info("【登录失败】-用户账号：{}已锁定",username);
            responseModel.setCode(ResponseModel.USERLOCK);
            responseModel.setMsg("用户已锁定");
            return JSON.toJSONString(responseModel);
        }
        if (!user.getPwd().equals(password)){
            log.info("【登录失败】-用户账号：{}密码错误",username);
            responseModel.setCode(ResponseModel.SECRETERROR);
            responseModel.setMsg("用户密码错误");
            return JSON.toJSONString(responseModel);
        }
        log.info("【登录成功】-用户账号{}",username);
        String token= JWTUtil.sign(username,JWTUtil.SECRET);
        responseModel.setCode(ResponseModel.LOGINSUCEESS);
        responseModel.setMsg("登录成功");
        Map<String,String> rData=new HashMap<>();
        if (rememberMe.equals("true")){
            rememberMe= UUID.randomUUID().toString();
//            redisUtil.set(rememberMe,token);
            rData.put("rememberMe",rememberMe);
        }
        rData.put("token",token);
        responseModel.setData(rData);
        return JSON.toJSONString(responseModel);
    }

    @RequestMapping("/test")
    @RequiresPermissions(value={"test"},logical = Logical.OR)
    public @ResponseBody
    String test(){
        return "test";
    }

    @RequestMapping("/403")
    public String noAuth(){
        return "403";
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public Object unauth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return map;
    }

    @RequestMapping("/loginPage")
    public String login(){

        return "pages/login";
    }
}
