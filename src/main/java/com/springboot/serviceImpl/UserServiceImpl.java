package com.springboot.serviceImpl;

import com.springboot.entity.UserEntity;
import com.springboot.mapper.UserMapper;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserMapper userMapper;
    @Override
    public UserEntity loginIn(String userName, String password) {
        return userMapper.getUserByNameAndPassword(userName,password);
    }
}
