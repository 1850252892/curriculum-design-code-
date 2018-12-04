package com.shop.module.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



//@EnableEurekaClient
//@EnableDiscoveryClient
//@EnableFeignClients(basePackages="com.shop.sericefeign")
//@EnableCasClient
@SpringBootApplication
@MapperScan("com.shop.module.user.dao")
public class
UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
