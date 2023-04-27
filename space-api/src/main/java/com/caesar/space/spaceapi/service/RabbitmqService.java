package com.caesar.space.spaceapi.service;

public interface RabbitmqService {
    void mqUploadFileMessage(String routingKey, Object message);
}
