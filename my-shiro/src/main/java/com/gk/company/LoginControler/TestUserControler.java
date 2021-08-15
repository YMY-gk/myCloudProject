package com.gk.company.LoginControler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(value="测试接口Controller")
public class TestUserControler {
    @GetMapping("/sayHi1")
    @ApiOperation(value="测试用接口", notes="测试用接口" )
    @RequiresPermissions("admin:add")
    @RequiresRoles("admin")
    public String sayhello(){
        return "I`m provider 1 ,Hello consumer!";
    }
    @GetMapping("/sayHi")
    @ApiOperation(value="测试用接口", notes="测试用接口" )
    @RequiresPermissions("admin:eedd")
    @RequiresRoles("admin2")
    public String sayHi(){
        return "I`m provider 1 ,Hello consumer!";
    }
    @GetMapping("/sayHaha")
    @ApiOperation(value="测试用接口", notes="测试用接口")
    public String sayHaha(){
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        subject.checkPermission("admin:add");
        return "I`m provider 1 ,Hello consumer!";
    }
    
}
