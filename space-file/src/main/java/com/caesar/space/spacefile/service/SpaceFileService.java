package com.caesar.space.spacefile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.caesar.space.spaceapi.responce.JsonResponse;
import com.caesar.space.spacefile.domain.SpaceFile;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 刘文康
* @description 针对表【idea_file_t】的数据库操作Service
* @createDate 2023-04-21 09:29:35
*/
public interface SpaceFileService extends IService<SpaceFile> {

    JsonResponse<?> uploadFileLimit(MultipartFile multipartFile, int limit);
}
