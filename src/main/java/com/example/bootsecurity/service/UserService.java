package com.example.bootsecurity.service;

import com.example.bootsecurity.model.User;
import com.example.bootsecurity.model.response.LoginResponseDto;

public interface UserService {
    LoginResponseDto loginForJwt(User user);
}
