package com.springboot.controller;

import com.springboot.entity.UserEntity;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    //将Service注入Web层
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String show(){
        return "login";
    }

    @RequestMapping(value = "/loginIn",method = RequestMethod.POST)
    public String login(String name,String password){
        UserEntity userBean = userService.loginIn(name,password);
        if(userBean!=null){
            return "success";
        }else {
            return "error";
        }
    }
}
