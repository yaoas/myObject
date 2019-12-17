package com.springboot.testLayuiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 前端控制器
 * @author: xu zhihao
 * @create: 2019-06-14 10:36
 */
@Controller
@RequestMapping("/hello")
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

}
