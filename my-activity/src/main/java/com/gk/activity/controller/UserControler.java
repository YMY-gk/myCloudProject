package com.gk.activity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *@ClassName LoginControler
 *@Deacription TODO
 *@Author
 *@Date 2021/3/31 17:34
 *@Version 1.0
 **/
@RestController
public class UserControler {
    @Bean
    @LoadBalanced
    public RestTemplate getResttemplate(){
        return new RestTemplate();
    }
    @Autowired
    private RestTemplate resttemplate;

    @RequestMapping("/hello")
    public String hello(){
        //指出服务地址   http://{服务提供者应用名名称}/{具体的controller}
        String url="http://company/user/sayHello";

        //返回值类型和我们的业务返回值一致
        return resttemplate.getForObject(url, String.class);

    }
    @RequestMapping("/hi")
    public String hi(){
        //指出服务地址   http://{服务提供者应用名名称}/{具体的controller}
        String url="http://company/user/sayHi";

        //返回值类型和我们的业务返回值一致
        return resttemplate.getForObject(url, String.class);

    }
    @RequestMapping("/haha")
    public String haha(){
        //指出服务地址   http://{服务提供者应用名名称}/{具体的controller}
        String url="http://company/user/sayHaha";
        //返回值类型和我们的业务返回值一致
        return resttemplate.getForObject(url, String.class);

    }
    
}
