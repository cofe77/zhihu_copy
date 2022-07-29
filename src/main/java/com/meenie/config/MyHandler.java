package com.meenie.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.meenie.common.BaseUserInfo;
import com.meenie.controller.ResCode;
import com.meenie.exception.SystemException;
import com.meenie.util.JWTUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(null==token||token.equals("{}")||"[object Object]".equals(token)){
            throw new SystemException(ResCode.SYSTEM_ERR,"未登录",null);
        }
        try{
            DecodedJWT tokenInfo = JWTUtils.getTokenInfo(token);
            String username = tokenInfo.getClaim("username").asString();
            String id = tokenInfo.getClaim("id").asString();
            BaseUserInfo.set("username",username);
            BaseUserInfo.set("id",id);
            return true;
        } catch (Exception e){
            throw new SystemException(ResCode.SYSTEM_ERR,"已过期，请重新登录",null);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
