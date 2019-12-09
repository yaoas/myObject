package com.springboot.controller;

import com.springboot.entity.UserEntity;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
    //取消点赞
    @RequestMapping("/testUpload")
    @ResponseBody
    public  Object testUpload(HttpServletRequest request , MultipartFile file ){
        if (file.isEmpty()) return "上传失败，请选择文件";
        //上传到服务器
        String filePath = "/opt/lvyouimages/imagess/";
        //生成唯一的文件名
        //生成文件在服务器存放的名字
        String fileSuffix =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+fileSuffix;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
          String returnPath = "http://39.97.237.246/imagess/"+fileName;
        return returnPath;
    }
}
