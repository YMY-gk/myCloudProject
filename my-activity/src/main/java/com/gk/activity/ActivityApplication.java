package com.gk.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 *@ClassName CompanyApplication
 *@Deacription TODO
 *@Author os-guokui
 *@Date 2021/3/29 17:26
 *@Version 1.0
 **/

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrixDashboard //开启仪表盘
public class ActivityApplication {
        public static void main(String[] args) {
            SpringApplication.run(ActivityApplication.class, args);
        }
}
