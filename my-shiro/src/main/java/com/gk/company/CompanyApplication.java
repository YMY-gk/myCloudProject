package com.gk.company;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 *@ClassName CompanyApplication
 *@Deacription TODO
 *@Author os-guokui
 *@Date 2021/3/29 17:26
 *@Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.gk.company.*.mapper")
public class CompanyApplication {
        public static void main(String[] args) {
            SpringApplication.run(CompanyApplication.class, args);
        }
}
