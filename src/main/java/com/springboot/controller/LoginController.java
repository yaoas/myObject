package com.springboot.controller;

import com.springboot.common.Constant;
import com.springboot.common.FtpUtils;
import com.springboot.common.Utils;
import com.springboot.jwtShrio.JwtUtil;
import com.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Slf4j
@Controller
public class LoginController {
    //将Service注入Web层
    @Autowired
    UserService userService;

    @RequestMapping("/loginsss")
    public String show(){
        return "login";
    }

  /*  @RequestMapping(value = "/loginIn",method = RequestMethod.POST)
    public String login(String name,String password){
        UserEntity userBean = userService.loginIn(name,password);
        if(userBean!=null){
            return "success";
        }else {
            return "error";
        }
    }*/
  @RequestMapping("/login")
  public ResponseEntity<Map<String, String>> login(String username, String password) {
      log.info("username:{},password:{}",username,password);
      Map<String, String> map = new HashMap<>();
      if (!"tom".equals(username) || !"123".equals(password)) {
          map.put("msg", "用户名密码错误");
          return ResponseEntity.ok(map);
      }
      JwtUtil jwtUtil = new JwtUtil();
      Map<String, Object> chaim = new HashMap<>();
      chaim.put("username", username);
      String jwtToken = jwtUtil.encode(username, 5 * 60 * 1000, chaim);
      map.put("msg", "登录成功");
      map.put("token", jwtToken);
      return ResponseEntity.ok(map);
  }
    @RequestMapping("/testdemo")
    public ResponseEntity<String> testdemo() {
        return ResponseEntity.ok("我爱蛋炒饭");
    }
    //自定义上传图片
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
    //ftp上传图片
    @RequestMapping("/ftpUploadFile")
    @ResponseBody
    public  Object ftpUploadFile(HttpServletRequest request,MultipartFile file  ){
        String fileSuffix =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+fileSuffix;
        String path = "";
        try {
            boolean connect = FtpUtils.connect(Constant.FTP_PATH, Constant.FTP_ADDRESS, Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASSWORD);
            File files = Utils.multipartFileToFile(file);
            path = FtpUtils.upload(files,fileName);
            files.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
    //ftp下载图片
    @RequestMapping("/ftpUploadFiless")
    @ResponseBody
    public  Object ftpUploadFiless(HttpServletRequest request,MultipartFile file ,HttpServletResponse response ){

        try {
            boolean connect = FtpUtils.connect(Constant.FTP_PATH, Constant.FTP_ADDRESS, Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASSWORD);
            OutputStream oooo = response.getOutputStream();
            FtpUtils.downFileByFtp("/webapps/longman/upload/2627e375-6cca-4103-be0b-b87dce43c733.png",oooo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    @RequestMapping("/ftpDeleteFile")
    @ResponseBody
    public  Object ftpDeleteFile(HttpServletRequest request,String url){
        int i = 1;
        try {
            boolean connect = FtpUtils.connect(Constant.FTP_PATH, Constant.FTP_ADDRESS, Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASSWORD);
            i = FtpUtils.deleteFile(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(i>0){
            return "成功";
        }else {
            return "失败";
        }
    }
}
