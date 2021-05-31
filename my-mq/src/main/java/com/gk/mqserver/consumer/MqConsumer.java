package com.gk.mqserver.consumer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/5/6 0:34
 */
@RestController
public class MqConsumer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 简单方式
     * @param exchage
     * @param query
     * @param msg
     */
    public void  getMassege(String exchage,String query,String msg){
        //rabbitTemplate.convertAndSend();


    }
}
