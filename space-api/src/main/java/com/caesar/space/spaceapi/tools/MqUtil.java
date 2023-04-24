package com.caesar.space.spaceapi.tools;

import com.caesar.space.spaceapi.constant.ServiceConstant;
import com.caesar.space.spaceapi.service.RabbitmqService;
import com.google.common.collect.Multimap;
import org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public String sendUploadMessage(MultipartFile multipartFile) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("file",multipartFile.getResource()); // this is spring multipart file
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "multipart/form-data");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(ServiceConstant.IDEA_SPACE_FILE.getCode() + "/file/upload", requestEntity, String.class);
        HashMap<String, String> messageMap = new HashMap<>();
        messageMap.put("code","1001");
        messageMap.put("fileId","upload");
        rabbitmqService.mqUploadFileMessage(UPLOAD_ROUTE_KEY,messageMap);
        return stringResponseEntity.getBody();
    }
}
