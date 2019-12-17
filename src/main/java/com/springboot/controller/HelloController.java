package com.springboot.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
public class HelloController {
    private static Logger log = Logger.getLogger(HelloController.class);

    @RequestMapping("/index")
    public String sayHello(){
        return "index";
    }

    @RequestMapping(value = "/hellos", method = RequestMethod.GET)
    @ResponseBody
    public String hello(@RequestParam String name,HttpServletResponse response) {
return "";

    }

    public  static void main(String[] args){
        File file = new File("导出excel例子.xls");
        file.delete();
    }

}
