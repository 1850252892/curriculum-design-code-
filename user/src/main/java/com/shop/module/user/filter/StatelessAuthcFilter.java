package com.shop.module.user.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shop.module.user.model.ResponseModel;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class StatelessAuthcFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        String username = req.getParameter("username");
        String password= req.getParameter("pwd");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ResponseModel model=new ResponseModel();

        if (authorization==null||authorization=="")
            onNotLogin(response);
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        JSONObject jsonObjec=new JSONObject();

            Subject subject = this.getSubject(request, response);
            subject.login(token);
            jsonObjec.put("token:",subject.getSession().getId());
            model.setCode(401);
            model.setMsg("token : username--"+username+"--password--"+password);
            model.setData(jsonObjec.toJSONString());
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            httpResponse.getWriter().write(JSON.toJSONString(model));
            return  false;
    }

    //登录失败时默认返回401状态码
    private void onTokenError(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResponseModel model=new ResponseModel();
        model.setCode(401);
        model.setMsg("token error");
        model.setData("");
        httpResponse.getWriter().write(JSON.toJSONString(model));
    }

    //登录失败时默认返回401状态码
    private void onNotLogin(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResponseModel model=new ResponseModel();
        model.setCode(200);
        model.setMsg("not login");
        model.setData("");
        httpResponse.getWriter().write(JSON.toJSONString(model));
    }
}
