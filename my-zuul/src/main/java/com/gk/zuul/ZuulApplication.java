package com.gk.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *@ClassName CompanyApplication
 *@Deacription TODO
 *@Author os-guokui
 *@Date 2021/3/29 17:26
 *@Version 1.0
 **/

@SpringBootApplication
@EnableZuulProxy  //开启zuul代理
@EnableEurekaClient        //zuul服务要注册到Eureka上
public class ZuulApplication {
        public static void main(String[] args) {
            SpringApplication.run(ZuulApplication.class, args);
        }
}
