package com.gk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *@ClassName CompanyApplication
 *@Deacription TODO
 *@Author os-guokui
 *@Date 2021/3/29 17:26
 *@Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class EmailApplication {
        public static void main(String[] args) {
            SpringApplication.run(EmailApplication.class, args);
        }
}
