package com.example.bootsecurity.controller;

import com.example.bootsecurity.common.Result;
import com.example.bootsecurity.model.User;
import com.example.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/login")
    public Result login(User user){
        return Result.success(userService.loginForJwt(user));
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
