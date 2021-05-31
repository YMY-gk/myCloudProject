package com.gk.eruka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *@ClassName ErukaApplication
 *@Deacription TODO
 *@Author os-guokui
 *@Date 2021/3/29 17:26
 *@Version 1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class ErukaApplication {
        public static void main(String[] args) {
            SpringApplication.run(ErukaApplication.class, args);
        }
}
