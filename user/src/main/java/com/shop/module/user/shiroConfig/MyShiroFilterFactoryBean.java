package com.shop.module.user.shiroConfig;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.crazycake.shiro.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    private String filterChain="SHIRO_FILTER_CHAIN";
    private RedisManager redisManager;

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public Map<String, String> getFilterChainDefinitionMap() {
        Map<String,String> map;
        byte[] key = this.filterChain.getBytes();
        map = (Map<String, String>) SerializeUtils.deserialize(this.redisManager.get(key));
        return map;
    }

    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        if (filterChainDefinitionMap!=null){
            byte[] key = this.filterChain.getBytes();
            byte[] value = SerializeUtils.serialize(filterChainDefinitionMap);
            this.redisManager.set(key,value);
            logger.info("存入filterChain成功");
        }
    }
}
