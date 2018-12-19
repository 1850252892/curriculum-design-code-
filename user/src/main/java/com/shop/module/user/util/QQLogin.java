package com.shop.module.user.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@PropertySource(value = "classpath:application.yml")
public class QQLogin {

    @Value("${shop.qqLogin.url}")
    private String url;
    @Value("${shop.qqLogin.response-type}")
    private String response_type;
    @Value("${shop.qqLogin.client-id}")
    private String client_id;
    @Value("${shop.qqLogin.redirect-url}")
    private String redirect_rul;
    @Value("${shop.qqLogin.scope}")
    private String scope;
    @Value("${shop.qqLogin.getOpenIdUrl}")
    private String QQ_GET_OPENID_URL;
    @Value("${shop.qqLogin.getUerInfo}")
    private String QQ_GET_USER_INFO_URL;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRedirect_rul() {
        return redirect_rul;
    }

    public void setRedirect_rul(String redirect_rul) {
        this.redirect_rul = redirect_rul;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRequestUrl() {
        return url+"?response_type="+response_type+"&client_id="+client_id+"&redirect_uri="+redirect_rul+"&scope="+scope;
    }

    public String getOpenId(String accessToken){
        String openIdRsult= HttpRequestUtil.sendGet(QQ_GET_OPENID_URL,"access_token="+accessToken);
        if (openIdRsult.equals("e")){
            return "e";
        }
        int start=openIdRsult.indexOf("(");
        int end=openIdRsult.indexOf(")");
        openIdRsult=openIdRsult.substring(start+1,end);
        Map<String,String> openIdMap = (Map<String, String>) JSON.parse(openIdRsult);
        if (openIdMap.containsKey("error")){
            //请求错误
            return "e";
        }
        String openId=openIdMap.get("openid");
//        String clientId=openIdMap.get("client_id");
        return openId;
    }

    public Map<String,String> getUserInfo(String accessToken,String openId){
        String userInfoResult=HttpRequestUtil.sendGet(QQ_GET_USER_INFO_URL,"access_token="+accessToken+"&oauth_consumer_key="+client_id+"&openid="+openId);
        Map<String,String> result=new HashMap<>();
        if (userInfoResult.equals("e")){
           result.put("e","");
            return result;
        }
        result= (Map<String, String>) JSON.parse(userInfoResult);
        return result;
    }
}
