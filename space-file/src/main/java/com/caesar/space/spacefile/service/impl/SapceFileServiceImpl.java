package com.caesar.space.spacefile.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caesar.space.spaceapi.annotations.OperationLog;
import com.caesar.space.spaceapi.exception.ServiceException;
import com.caesar.space.spaceapi.util.IpUtil;
import com.caesar.space.spaceapi.util.RedisUtil;
import com.caesar.space.spacefile.config.COSConfig;
import com.caesar.space.spaceapi.domain.SpaceFile;
import com.caesar.space.spacefile.mapper.SpaceFileMapper;
import com.caesar.space.spacefile.service.SpaceFileService;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * @author 刘文康
 * @description 针对表【idea_file_t】的数据库操作Service实现
 * @createDate 2023-04-21 09:29:35
 */
@Slf4j
@Service
public class SapceFileServiceImpl extends ServiceImpl<SpaceFileMapper, SpaceFile>
        implements SpaceFileService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SpaceFileMapper spaceFileMapper;

    @Autowired
    COSConfig cosConfig;

    @Autowired
    COSClient cosClient;


    @OperationLog(code = "uploadFile", value = "上传头像")
    @Override
    public String uploadFileLimit(MultipartFile multipartFile, int limit, HttpServletRequest request) {
        String ipaddr = IpUtil.getIpAddr(request);
        int uploadTimesInDay = redisUtil.get(ipaddr);
        if (!fileLimits(limit, ipaddr, uploadTimesInDay)) {
            return "FAILED";
        }
        return uploadFile(multipartFile);
    }

    @Transactional
    @OperationLog(code = "uploadFile", value = "上传文件")
    public String uploadFile(MultipartFile multipartFile) {
        // spring直接使用File接收文件传参，会有问题(No primary or single unique constructor found for class java.io.File)不知道具体原因，之后再看。
        // 腾讯云上传方法参数需要File,做一个转换操作
        File file;
        String key;
        String downloadUrl;
        try {
            file = MultipartFileToFile(multipartFile);
            if (file == null) {
                throw new ServiceException("null file");
            }
            key = file.getName();
            PutObjectRequest putObjectRequest = new PutObjectRequest(cosConfig.getBucketName(), key, file);
            // 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 examplebucket-1250000000.cos.ap-guangzhou.myqcloud.com/images/picture.jpg 中，对象键（key）为 images/picture.jpg
            // 如果images文件夹不存在则创建
            cosClient.putObject(putObjectRequest);
            downloadUrl = cosConfig.getCosHost() + "/" + key;
            // 数据落库
            saveUploadInfo(key, downloadUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传文件异常: " + e.getMessage();
        }

        // ResponseParam为自定义返回json格式
        return downloadUrl;
    }

    private void saveUploadInfo(String key, String downloadUrl) {
        SpaceFile spaceFile = new SpaceFile(null, 1650037476670177280L, downloadUrl,
                key.substring(key.lastIndexOf(".") + 1), 1, "001", new Date(), new Date());
        UpdateWrapper<SpaceFile> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", 1650037476670177280L);
        SpaceFile entity = new SpaceFile();
        entity.setIsLatest(0);
        // 将之前的图片设置为非最新
        spaceFileMapper.update(entity, wrapper);
        // 插入最新数据
        spaceFileMapper.insert(spaceFile);
    }

    /**
     * 接口只能接受MultipartFile, 腾讯云需要File
     * 故 MultipartFile => File
     *
     * @param multiFile 上传文件
     * @return file
     */
    public static File MultipartFileToFile(MultipartFile multiFile) throws Exception {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String contentType = multiFile.getContentType();
        try {
            // 防止生成的临时文件重复,文件名随机码, UUID
            File file = File.createTempFile(UUID.randomUUID().toString().replaceAll("-", ""), suffix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("MultipartFileToFile 文件转换异常");
        }
    }


    private boolean fileLimits(int limit, String ipaddr, int uploadTimesInDay) {
        if (uploadTimesInDay >= limit) {
            return false;
        }
        if (uploadTimesInDay == 0) {
            redisUtil.set(ipaddr);
        } else {
            redisUtil.increment(ipaddr);
        }
        return true;
    }
}




