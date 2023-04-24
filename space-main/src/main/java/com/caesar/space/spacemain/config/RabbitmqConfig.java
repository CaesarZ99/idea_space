package com.caesar.space.spacemain.config;

import com.caesar.space.spaceapi.constant.RabbitmqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h3>RaabbitmqConfiguration</h3>
 * <p>rabbitmq 配置</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-17 16:39
 **/
@Configuration
public class RabbitmqConfig {

    /*交换机*/
    @Bean("bootExchange")
    public Exchange bootExchange(){
        return ExchangeBuilder.topicExchange(RabbitmqConstant.EXCHANGE_BOOT.getCode()).durable(true).build();
    }
    /* 2.Queue 队列*/
    @Bean("bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(RabbitmqConstant.QUEUE_BOOT.getCode()).build();
    }
    /*队列和交换机绑定关系*/
    @Bean
    public Binding getBinding(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }



    /*file交换机*/
    @Bean("fileExchange")
    public Exchange fileExchange(){
        return ExchangeBuilder.topicExchange(RabbitmqConstant.EXCHANGE_FILE.getCode()).durable(true).build();
    }
    /* 2.Queue 队列*/
    @Bean("fileQueue")
    public Queue fileQueue(){
        return QueueBuilder.durable(RabbitmqConstant.QUEUE_FILE.getCode()).build();
    }
    /*队列和交换机绑定关系*/
    @Bean
    public Binding fileBinding(@Qualifier("fileQueue") Queue queue, @Qualifier("fileExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("file.#").noargs();
    }
}
