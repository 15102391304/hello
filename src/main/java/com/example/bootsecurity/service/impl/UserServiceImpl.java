package com.example.bootsecurity.service.impl;

import com.example.bootsecurity.common.exception.ServiceException;
import com.example.bootsecurity.mapper.UserMapper;
import com.example.bootsecurity.model.User;
import com.example.bootsecurity.model.UserExample;
import com.example.bootsecurity.model.response.LoginResponseDto;
import com.example.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginResponseDto loginForJwt(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(user.getName())
                .andPasswordEqualTo(user.getPassword());
        List<User> users=userMapper.selectByExample(userExample);
        if(null!=users&&!users.isEmpty()){
            LoginResponseDto responseDto=new LoginResponseDto();
            responseDto.setToken("123");
          return responseDto;
        }
        //登陆失败
        throw new ServiceException("登陆失败");
    }
}
