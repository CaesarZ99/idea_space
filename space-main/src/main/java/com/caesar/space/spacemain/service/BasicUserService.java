package com.caesar.space.spacemain.service;

import com.caesar.space.spaceapi.domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface BasicUserService {

    /**
     * 通过userId 获取单个用户信息
     *
     * @param userId userId
     * @return User
     */
    User gerSingleUserInfo(Long userId);

    /**
     * 通过调用space-file文件服务接口上传文件
     * 用户头像上传等
     *
     * @param file file
     * @return String
     */
    String uploadFileBySpaceFile(MultipartFile file);

    /**
     * 通过调用rabbitmq 消息队列异步上传文件
     * 异步请求, 用于用户页大文件上传等情况
     *
     * @param file file
     * @return
     */
    void uploadFileByMq(MultipartFile file);
}
