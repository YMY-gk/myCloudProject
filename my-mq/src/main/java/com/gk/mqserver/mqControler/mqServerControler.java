package com.gk.mqserver.mqControler;

import com.gk.mqserver.config.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.amqp.rabbit.core.RabbitAdmin.QUEUE_NAME;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/25 15:25
 * mq存在六种模式
 * 1、简单队列，这种模式只是单纯处理，只需要有定义一个队列名就可以
 * 2、work 工作队列，也叫任务队列属于一对多队列，当一个被消费时其他消费者不能被消费，这时需要将mq模式设置成work
 * 定义一下对应的队名名
 *
 * 3、广播模式，这时是需要一个交换器进行队列数据分配，这时队列并不是最重要的，交换机是最重要的我们生产者需要定义对应交换机模式，
 * 交换器主要有四种类型:direct、fanout、topic、headers，这里的交换器是 fanout。
 * 4、路由模式　生产者将消息发送到direct交换器，在绑定队列和交换器的时候有一个路由key，生产者发送的消息会指定一个路由key，那么消息只会发送到相应key相同的队列，接着监听该队列的消费者消费消息。
 *5、topic模式，生产者需要定义交换机名和选择对应模式，定义队列名、绑定路由名；
 *
 * 集群处理：
 * 普通集群：副本集群
 * 镜像集群：
 */
@RestController("/mq")
public class mqServerControler {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 简单方式
     * @param query
     * @param msg
     */
    @PostMapping("/mqUtilSend")
    public void  sendMassege(String query,String msg) throws Exception {
        //rabbitTemplate.convertAndSend();
        //1、获取连接
        Connection connection = ConnectionUtil.getConnection();
        //2、声明信道
        Channel channel = connection.createChannel();
        //3、声明(创建)队列
        /**
         *.Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive,
         *  boolean autoDelete, Map<String, Object> arguments) throws IOException;
         *  queue: 队列的名称
         *
         * durable: 设置是否持久化, true表示队列为持久化, 持久化的队列会存盘, 在服务器
         * 重启的时候会保证不丢失相关信息
         *
         * exclusive: 设置是否排他, true表示队列为排他的, 如果一个队列被设置为排他队列,
         * 该队列仅对首次声明它的连接可见, 并在连接断开时自动删除,
         * (这里需要注意三点:1.排他队列是基于连接Connection可见的,
         * 同一个连接的不同信道Channel是可以同时访问同一连接创建的排他队列;
         * "首次"是指如果一个连接己经声明了一个排他队列，其他连接是不允许建
         * 立同名的排他队列的，这个与普通队列不同;即使该队列是持久化的，一旦
         * 连接关闭或者客户端退出，该排他队列都会被自动删除，这种队列适用于
         * 一个客户端同时发送和读取消息的应用场景)
         *
         * autoDelete: 设置是否自动删除。为true 则设置队列为自动删除。自动删除的前提是,
         * 至少有一个消费者连接到这个队列，之后所有与这个队列连接的消费者都断开时，才会自
         * 动删除。不能把这个参数错误地理解为: "当连接到此队列的所有客户端断开时，这个队
         * 列自动删除"，因为生产者客户端创建这个队列，或者没有消费者客户端与这个队列连接
         * 时，都不会自动删除这个队列。
         *
         * arguments: 设置队列的其他一些参数, 如 x-message-ttl等
         *
         */
        channel.queueDeclare(query, false, false, false, null);
        //4、定义消息内容
        String message = msg;
        //5、发布消息
        channel.basicPublish("", query, null, message.getBytes());
        System.out.println("[x] Sent'" + message + "'");
        //6、关闭通道
        channel.close();
        //7、关闭连接
        connection.close();
    }

}
