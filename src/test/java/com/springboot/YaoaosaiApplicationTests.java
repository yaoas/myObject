package com.springboot;

import com.springboot.entity.UserEntity;
import com.springboot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class YaoaosaiApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        UserEntity userBean = userService.loginIn("a","a");
        System.out.println("该用户ID为：");
        System.out.println(userBean.getId());
    }

}
