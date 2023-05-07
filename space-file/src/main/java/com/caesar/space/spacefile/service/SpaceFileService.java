package com.caesar.space.spacefile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.caesar.space.spaceapi.domain.SpaceFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author 刘文康
* @description 针对表【idea_file_t】的数据库操作Service
* @createDate 2023-04-21 09:29:35
*/
public interface SpaceFileService extends IService<SpaceFile> {

    String uploadFileLimit(MultipartFile multipartFile, int limit, HttpServletRequest request);
}
