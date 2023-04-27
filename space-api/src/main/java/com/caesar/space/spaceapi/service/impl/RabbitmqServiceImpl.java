package com.caesar.space.spaceapi.service.impl;

import com.caesar.space.spaceapi.service.RabbitmqService;
import com.caesar.space.spaceapi.constant.RabbitmqConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h3>RabbitmqServiceImpl</h3>
 * <p>rabbitmq封装接口实现类</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-23 12:46
 **/
@Service
public class RabbitmqServiceImpl implements RabbitmqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendSignUpMailCode(String routingKey, Object message) {
        rabbitTemplate.convertAndSend(RabbitmqConstant.EXCHANGE_BOOT.getCode(), routingKey, message);
    }

}
