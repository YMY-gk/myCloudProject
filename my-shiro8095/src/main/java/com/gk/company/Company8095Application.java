package com.gk.company;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *@ClassName CompanyApplication
 *@Deacription TODO
 *@Author os-guokui
 *@Date 2021/3/29 17:26
 *@Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.gk.*.dao")
public class Company8095Application {
        public static void main(String[] args) {
            SpringApplication.run(Company8095Application.class, args);
        }
}
