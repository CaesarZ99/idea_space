package com.caesar.space.spaceapi.service;

public interface RabbitmqService {
    /**
     * 消息异步通知发送注册邮箱验证码
     *
     * @param routingKey routingKey
     * @param message message
     */
    void sendSignUpMailCode(String routingKey, Object message);
}
