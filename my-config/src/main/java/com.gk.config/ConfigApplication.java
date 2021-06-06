package com.gk.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *@ClassName ConfigApplication
 *@Deacription TODO
 *@Author os-guokui
 *@Date 2021/3/29 17:26
 *@Version 1.0
 **/

@SpringBootApplication
//@EnableConfigServer      //zuul服务要注册到Eureka上
public class ConfigApplication {
        public static void main(String[] args) {
            SpringApplication.run(ConfigApplication.class, args);
        }
}
