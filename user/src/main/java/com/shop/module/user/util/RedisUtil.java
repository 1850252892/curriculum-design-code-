package com.shop.module.user.util;

import org.crazycake.shiro.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisUtil {
    private static Logger logger= LoggerFactory.getLogger(RedisUtil.class);

    private String redisAddr = "127.0.0.1";
    private int port = 6379;
    private int expire = 0;
    private int timeout = 0;
    private  String AUTH = ""; //redis密码，通过服务器里redis配置文件里设置
    private  int MAX_TOTAL = 300;//最大连接数
    private int MAX_IDLE = 200;//最大空闲连接数
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出
    private int MAX_WAIT = 10000;
    private  int TIMEOUT = 10000;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private  boolean TEST_ON_BORROW = true;
    private  JedisPool jedisPool = null;

    public void init(){
        logger.info("--redisPool init");
        logger.info("--配置信息如下:");
        logger.info("--redisAddr:{} |  port:{} | max_total:{} | max_idle:{} | max_wait:{} | timeout:{}",
                redisAddr,port,MAX_TOTAL,MAX_IDLE,MAX_WAIT,TIMEOUT);
        logger.info("--配置信息打印结束:");

        try{
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            if (AUTH!=null && AUTH!="")
                jedisPool = new JedisPool(config,redisAddr,port,TIMEOUT,AUTH);  // 有密码的情况
            else
                jedisPool = new JedisPool(config,redisAddr,port,TIMEOUT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized  Jedis getJedis(){
        //连接池中获取资源
        try{
            if (jedisPool != null){
                Jedis jedis = jedisPool.getResource();
                return jedis;
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //释放jedis和jedisPool资源
    public void releaseResource(final Jedis jedis){
        if (jedis == null){
            jedis.close();
        }
        if (jedisPool == null){
            jedisPool.close();
        }
    }

    public void set(String key,String value){
        if (isNull(key,value))
            return;
        byte[] k=key.getBytes();
        byte[] v=value.getBytes();
        Jedis jedis=getJedis();
        try {
            jedis.set(k,v);
        }finally {
            releaseResource(jedis);
        }
    }

    public void set(String key,Object value){
        if (isNull(key,value))
            return;
        byte[] k=key.getBytes();
        byte[] v= SerializeUtils.serialize(value);
        Jedis jedis=getJedis();
        try {
            jedis.set(k,v);
        }finally {
            releaseResource(jedis);
        }
    }

    public void set(String key,Object value,int time){
        if (isNull(key,value))
            return;
        byte[] k=key.getBytes();
        byte[] v= SerializeUtils.serialize(value);
        Jedis jedis=getJedis();
        try {
            jedis.set(k,v);
            if (time != 0) {
                jedis.expire(key, time);
            }
        }finally {
            releaseResource(jedis);
        }
    }

    public String get(String key){
        byte[] k=key.getBytes();
        Jedis jedis=getJedis();

        byte[] v;
        String value;
        try {
            v=jedis.get(k);
            value= new String(v);
        }finally {
            releaseResource(jedis);
        }
        return value;

    }

    public Object getObject(String key){
        byte[] k=key.getBytes();
        Jedis jedis=getJedis();
        Object value;
        try {
            value=SerializeUtils.deserialize(jedis.get(k));
        }finally {
            releaseResource(jedis);
        }
        return value;
    }
    public void del(String key) {
        byte[] k=key.getBytes();
        Jedis jedis=getJedis();
        try {
            jedis.del(k);
        } finally {
            releaseResource(jedis);
        }
    }
    public Boolean exists(String key){
        byte[] k=key.getBytes();
        Jedis jedis=getJedis();
        Boolean bl=false;
        try {
            bl=jedis.exists(k);
        }finally {
            releaseResource(jedis);
        }
        return bl;
    }

    public void flushDB() {
        Jedis jedis=getJedis();
        try {
            jedis.flushDB();
        } finally {
            releaseResource(jedis);
        }
    }
    private boolean isNull(Object key,Object value){
        return key == null || value == null;
    }
}
