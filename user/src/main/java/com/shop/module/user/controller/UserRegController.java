package com.shop.module.user.controller;

import com.shop.module.user.entity.BaseUser;
import com.shop.module.user.model.ResponseModel;
import com.shop.module.user.model.ServiceModel;
import com.shop.module.user.model.UserModel;
import com.shop.module.user.service.serviceImpl.UserService;
import com.shop.module.user.util.QQLogin;
import com.shop.module.user.util.RedisUtil;
import com.shop.module.user.util.emiltool.EmailService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController("/register/")
public class UserRegController {
    static final String GET_VERCODE_TIME="verCodeTime_";
    static final String VERCODE="verCode_";
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;

    /**
     * @Author zhoulk
     * @Description // 获取验证码
     * @Date 13:22 2018/11/29
     *
     * @Param [account, regType]
     * @return com.shop.module.user.model.ResponseModel
     * @errorCode // 500:参数错误 400:操作频繁 401:账号已注册 402:验证码发送失败
     **/
    @GetMapping("getVerCode")
    public ResponseModel getVerifily(String account,Integer regType){
        ResponseModel responseModel=new ResponseModel();
        if (account==null || account.equals("") || regType==null || regType>1 ||regType <0){
            responseModel.setCode(500);
            responseModel.setMsg("参数错误");
            return responseModel;
        }
        String key=GET_VERCODE_TIME+account;
        Boolean isHis = redisUtil.exists(key);
        if (isHis){
            responseModel.setCode(400);
            Long hisTime = (Long) redisUtil.getObject(key);
            Map<String,Object> rData=new HashMap<>();
            rData.put("hisTime",hisTime);
            responseModel.setData(rData);
            responseModel.setMsg("请不要频繁操作");
            return responseModel;
        }
        Boolean accountIsUsed = userService.accountUse(account,regType);
        if (accountIsUsed){
            responseModel.setCode(401);
            responseModel.setMsg("账号已注册");
            return responseModel;
        }
        Long nowTime=new Date().getTime();
        redisUtil.set(key,nowTime,59);
        int code= new Random().nextInt(8000)+1234;//生成一个随机码
        key=VERCODE+account;
        redisUtil.set(key,code,60*30);
        if(account!=null&&!account.equals("")&&regType==2){
            Map<String, Object> map = new HashMap<>();
            map.put("time", new Date());
            map.put("message",code);
            map.put("toUserName", account);
            String statue=emailService.sendHtmlMail(account, "注册：验证码", map,"model.ftl");
            if (statue.equals("F")){
                responseModel.setCode(402);
                responseModel.setData(new byte[1]);
                responseModel.setMsg("验证码发送失败!!!请检查邮箱是否正确");
                return responseModel;
            }
        }
        responseModel.setCode(200);
        responseModel.setData(new byte[1]);
        responseModel.setMsg("验证码已发送，请注意查收!!!");
        return responseModel;
    }

    /**
     * @Author zhoulk
     * @Description //TODO
     * @Date 13:24 2018/11/29
     *
     * @Param [userModel]
     * @return com.shop.module.user.model.ResponseModel
     * @errorCode //TODO
     **/
    @PostMapping("addUsers")
    public ResponseModel addUsers(@RequestBody UserModel userModel){
        ResponseModel responseModel=ResponseModel.newResponseModel();
        String account = userModel.getAccount();
        String password = userModel.getPassword();
        Integer regType = userModel.getRegType();
        String verCode = userModel.getVerCode();
        password= DigestUtils.md5Hex(password);
        //参数校验

        String redisKey=VERCODE+userModel.getAccount();
        if (!redisUtil.exists(redisKey)){
            responseModel.setCode(400);
            responseModel.setMsg("验证码错误或已过期，请重新获取验证码!!!");
            return responseModel;
        }
        String realVerCode= redisUtil.getObject(redisKey) +"";
        if (realVerCode==null||realVerCode.equals("")||!realVerCode.equals(userModel.getVerCode())){
            responseModel.setCode(400);
            responseModel.setMsg("验证码错误或已过期，请重新获取验证码!!!");
            return responseModel;
        }
        Boolean accountIsUsed = userService.accountUse(userModel.getAccount(),userModel.getType());
        if (accountIsUsed){
            responseModel.setCode(401);
            responseModel.setMsg("账号已注册");
            return responseModel;
        }

        BaseUser baseUser=new BaseUser();
        baseUser.setAccount(account);
        baseUser.setPassword(password);
        if (regType==1)
            baseUser.setPhone(account);
        else
            baseUser.setEmail(account);
        baseUser.setType(regType);
        //调用service方法
        ServiceModel<String> regResult = userService.addBuyerUser(baseUser);
        if (!regResult.isSuccess()){
            responseModel.setCode(402);
            responseModel.setMsg(regResult.getErrMsg());
            return responseModel;
        }





        redisUtil.del(redisKey);
        responseModel.setCode(200);
        Map<String,String> map = new HashMap<>();
        map.put("account",regResult.getData());
        responseModel.setData(map);
        return responseModel;
    }


}
