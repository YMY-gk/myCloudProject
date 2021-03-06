package com.gk.company;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *@ClassName CompanyApplication
 *@Deacription TODO
 *@Author os-guokui
 *@Date 2021/3/29 17:26
 *@Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "com.gk.company.*")
@MapperScan(basePackages = "com.gk.company.*.mapper")
@EnableAsync(proxyTargetClass = true)
public class CompanyApplication {
        public static void main(String[] args) {
            SpringApplication.run(CompanyApplication.class, args);
        }
}
