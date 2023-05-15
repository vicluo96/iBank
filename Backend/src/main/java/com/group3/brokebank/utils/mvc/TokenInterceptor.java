package com.group3.brokebank.utils.mvc;

import com.group3.brokebank.entity.User;
import com.group3.brokebank.utils.exception.CustomException;
import com.group3.brokebank.utils.jwt.JWT;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(JWT.token);
        User user= JWT.getUser(token);
        if(user==null){
            throw new CustomException("Overtime or illegal token");
        }
        String newToken = JWT.sign(user);
        response.setHeader(JWT.token,newToken);
        request.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
