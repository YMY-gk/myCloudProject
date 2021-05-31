package com.gk.company.LoginControler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName LoginControler
 *@Deacription TODO
 *@Author
 *@Date 2021/3/31 17:34
 *@Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class TestUserControler {
    @RequestMapping("/sayHello")
    public String sayhello(){
        return "I`m provider 1 ,Hello consumer!";
    }
    @RequestMapping("/sayHi")
    public String sayHi(){
        return "I`m provider 1 ,Hello consumer!";
    }
    @RequestMapping("/sayHaha")
    public String sayHaha(){
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        subject.checkPermission("admin:add");
        return "I`m provider 1 ,Hello consumer!";
    }
    
}
