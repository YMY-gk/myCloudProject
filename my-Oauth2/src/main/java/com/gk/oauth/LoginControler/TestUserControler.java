package com.gk.oauth.LoginControler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value="测试接口Controller")
@RequestMapping("/user")
public class TestUserControler {
    @GetMapping("/sayHi1")
    @ApiOperation(value="测试用接口", notes="测试用接口" )
    public String sayhello(){
        return "I`m provider 1 ,Hello consumer!";
    }
    @GetMapping("/sayHi")
    @ApiOperation(value="测试用接口", notes="测试用接口" )
    public String sayHi(){
        return "I`m provider 1 ,Hello consumer!";
    }
    @GetMapping("/sayHaha")
    @ApiOperation(value="测试用接口", notes="测试用接口")
    public String sayHaha(){
        return "I`m provider 1 ,Hello consumer!";
    }
    
}
