package com.caesar.space.spaceapi.util;

import com.caesar.space.spaceapi.constant.ServiceConstant;
import com.caesar.space.spaceapi.exception.ServiceException;
import com.caesar.space.spaceapi.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * <h3>MqUtil</h3>
 * <p>消息队列工具类</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-23 12:53
 **/
@Component
public class MqUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitmqService rabbitmqService;

    public static final String UPLOAD_ROUTE_KEY = "boot.haha.hhh";

    public void sendSignUpMailCode(MultipartFile multipartFile) {
        MultiValueMap<String, Object> messageMap = new LinkedMultiValueMap<>();
        messageMap.add("file",multipartFile.getResource());
        rabbitmqService.sendSignUpMailCode(UPLOAD_ROUTE_KEY,messageMap);
    }
}
