package com.shop.module.user.config;

import com.shop.module.user.util.QQLogin;
import com.shop.module.user.util.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfig {
    @Bean
    public RedisUtil redisUtil(){
        RedisUtil redisUtil=new RedisUtil();
        redisUtil.init();
        return redisUtil;
    }


}
