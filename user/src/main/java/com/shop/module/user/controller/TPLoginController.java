package com.shop.module.user.controller;

import com.shop.module.user.model.ResponseModel;
import com.shop.module.user.util.QQLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TPLoginController {
    @Autowired
    QQLogin qqLogin;
    @GetMapping("qq")
    public ResponseModel getQQLoginUrl(){
        ResponseModel responseModel=new ResponseModel();
        responseModel.setCode(200);
        Map<String,String> map=new HashMap<>();
        map.put("url", qqLogin.getRequestUrl());
        responseModel.setData(map);
        return responseModel;
    }

}
