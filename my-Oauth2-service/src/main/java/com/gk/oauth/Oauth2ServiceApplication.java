package com.gk.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 *@ClassName Oauth2Application
 *@Deacription TODO
 *@Author os-guokui
 *@Date 2021/8/15 17:26
 *@Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
@EnableAsync(proxyTargetClass = true)
public class Oauth2ServiceApplication {
        public static void main(String[] args) {
            SpringApplication.run(Oauth2ServiceApplication.class, args);
        }
}
