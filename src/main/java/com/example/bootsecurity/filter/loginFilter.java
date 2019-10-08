package com.example.bootsecurity.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.bootsecurity.common.Result;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class loginFilter  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断登陆 没登陆 返回错误 登陆了放行
        String TOKEN= (String) request.getSession().getAttribute("TOKEN");
        if(TOKEN==null){
            response.setContentType("application/json; charset=utf-8");
            try (PrintWriter writer = response.getWriter()) {
                Result<Void> result = Result.fail("NO_LOGIN");
                writer.print(JSONObject.toJSON(result));
                writer.flush();
            }
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
