package com.gk.mqserver.config;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/5/6 0:48
 */

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 〈简述〉<br>
 * 〈连接RabbitMQ的工具类〉
 *
 * @create 2020/7/1
 * @since 1.0.0
 */
public class ConnectionUtil {

    public static Connection getConnection() throws Exception {
        //1、定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2、设置服务器地址
        factory.setHost("182.254.221.85");
        //3、设置端口
        factory.setPort(5672);
        //4、设置虚拟主机、用户名、密码
        factory.setVirtualHost("/mq-user");
        factory.setUsername("guest");
        factory.setPassword("StrongPassword");
        //5、通过连接工厂获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}