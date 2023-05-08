package com.caesar.space.spacefile.controller;

import com.caesar.space.spaceapi.responce.JsonResponse;
import com.caesar.space.spaceapi.util.RedisUtil;
import com.caesar.space.spaceapi.util.TimeUtil;
import com.caesar.space.spacefile.service.SpaceFileService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    public JsonResponse<?> upload(@RequestPart("file") MultipartFile multipartFile, @RequestParam("ipAddr") String ipAddr) {
        if (ObjectUtils.isEmpty(multipartFile)) {
            return JsonResponse.Builder.buildFailure("multipartFile is required");
        }
        Object uploadFileLimit = ideaFileService.uploadFileLimit(multipartFile, 10, ipAddr);
        if ("FAILED".equals(uploadFileLimit)) {
            return JsonResponse.Builder.buildFailure("您今日上传以超过次数限制: " + 10 + "次~ 请在 "
                    + TimeUtil.formatDateTime(redisUtil.getExpire(ipAddr)) + " 后重试");
        }
        return JsonResponse.Builder.buildSuccess(uploadFileLimit);
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
