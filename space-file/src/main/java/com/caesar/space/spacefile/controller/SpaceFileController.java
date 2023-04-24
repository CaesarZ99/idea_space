package com.caesar.space.spacefile.controller;

import com.caesar.space.spaceapi.responce.JsonResponse;
import com.caesar.space.spaceapi.tools.RedisUtil;
import com.caesar.space.spacefile.service.SpaceFileService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * <h3>FileController</h3>
 * <p>文件控制器</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-21 17:12
 **/
@RestController
@RequestMapping("file")
public class SpaceFileController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SpaceFileService ideaFileService;

    @Value("${tencent.cos.accessKey}")
    String string ;

    @PostMapping(value = "upload")
    public JsonResponse<?> upload(@RequestPart("file") MultipartFile multipartFile) {
        return ideaFileService.uploadFileLimit(multipartFile, 10);
    }

    @PostMapping(value = "/cosUpload")
    public String uploadCosFile(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        System.out.println(string);
        return null;
    }

    @RabbitListener(queues = "QUEUE_BOOT")
    public void downloadFile(HashMap<String, String> message) {
        System.out.println("收到下载请求:" + message.toString());
    }
}
