package com.shop.module.user.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


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
    private String requestUrl;

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

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
