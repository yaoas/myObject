package com.springboot.service;

import com.springboot.entity.UserEntity;

public interface UserService {
    UserEntity loginIn(String userName,String password);
}
