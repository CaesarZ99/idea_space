package com.caesar.space.spaceapi.service;

public interface RabbitmqService {
    public void mqUploadFileMessage(String routingKey, Object message);
}
