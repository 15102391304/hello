package com.example.bootsecurity.controller;

import com.example.bootsecurity.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/user")
    @ResponseBody
    public Result hello(HttpServletRequest request){
        request.getSession().setAttribute("TOKEN","123");
        return Result.success(null);
    }
    @RequestMapping("/hello")
    @ResponseBody
    public String he(){
        return "首页访问";
    }
}
