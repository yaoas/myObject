package com.springboot.controller;


import com.springboot.common.RedisConfig.RedisUtil;
import com.springboot.common.utils.R;
import com.springboot.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@RequestMapping("/hello")
public class HelloController {
    private static Logger log = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    RedisUtil redisUtil;
  @Autowired
  private SysLogService sysLogService;
    @RequestMapping("/index")
    public String sayHello(){
        return "index";
    }

    @RequestMapping(value = "/hellos")
    @ResponseBody
    public Object hello(@RequestParam String name, HttpServletResponse response, MultipartFile myFile) {
        redisUtil.set("wewe","343434");
//        log.info("info级别的日志");
           return R.ok();

    }

    public  static void main(String[] args){
        File file = new File("导出excel例子.xls");
        file.delete();
    }

}
