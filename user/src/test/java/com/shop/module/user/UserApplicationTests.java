package com.shop.module.user;

import com.alibaba.fastjson.JSON;
import com.shop.module.user.util.HttpRequestUtil;
import com.shop.module.user.util.RedisUtil;
import jdk.nashorn.internal.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserApplicationTests {

//    @Test
    public void contextLoads() {
       int a=1,b=2,c,d=0;
       if ((c=a)>0||(d=b)>0){
           System.out.println("d:"+d+"");
       }

        RedisUtil redisUtil=new RedisUtil();
       redisUtil.init();
       redisUtil.set("abc","abc",5);

    }

    @Test
    public void httpTest(){
        String s=HttpRequestUtil.sendGet("https://graph.qq.com/user/get_user_info","access_token=CC8587B2C266C07FA37F6FDA25B5DC22&oauth_consumer_key=101523415&openid=F96EC978E6FC5C9E21992880F670C333");
//        Object o=JSON.parse(s);
//        int start=s.indexOf("(");
//        int end=s.indexOf(")");
//        s=s.substring(start+1,end);
        Map<String,String> o= (Map<String, String>) JSON.parse(s);
        System.out.println(s+"---");
    }

}
