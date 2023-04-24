package com.caesar.space.spacefile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caesar.space.spaceapi.responce.JsonResponse;
import com.caesar.space.spaceapi.tools.IpUtil;
import com.caesar.space.spaceapi.tools.RedisUtil;
import com.caesar.space.spaceapi.tools.TimeUtil;
import com.caesar.space.spacefile.config.COSConfig;
import com.caesar.space.spacefile.domain.SpaceFile;
import com.caesar.space.spacefile.mapper.IdeaFileMapper;
import com.caesar.space.spacefile.service.SpaceFileService;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * @author 刘文康
 * @description 针对表【idea_file_t】的数据库操作Service实现
 * @createDate 2023-04-21 09:29:35
 */
@Slf4j
@Service
public class SapceFileServiceImpl extends ServiceImpl<IdeaFileMapper, SpaceFile>
        implements SpaceFileService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    COSConfig cosConfig;

    @Autowired
    COSClient cosClient;


    @Override
    public JsonResponse<?> uploadFileLimit(MultipartFile multipartFile, int limit) {
        String ipaddr = IpUtil.getIpAddr(request);
        int uploadTimesInDay = redisUtil.get(ipaddr);
        JsonResponse<?> limits = fileLimits(limit, ipaddr, uploadTimesInDay);
        if (limits != null) {
            return limits;
        }
        return JsonResponse.Builder.buildSuccess(uploadFile(multipartFile));
    }

    public String uploadFile(MultipartFile multipartFile){
        // spring直接使用File接收文件传参，会有问题(No primary or single unique constructor found for class java.io.File)不知道具体原因，之后再看。
        // 腾讯云上传方法参数需要File,做一个转换操作
        File file;
        String key;
        try {
            file = MultipartFileToFile(multipartFile);
            key = file.getName();
            PutObjectRequest putObjectRequest = new PutObjectRequest(cosConfig.getBucketName(), key, file);
            //对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 examplebucket-1250000000.cos.ap-guangzhou.myqcloud.com/images/picture.jpg 中，对象键（key）为 images/picture.jpg
            //如果images文件夹不存在则创建
            cosClient.putObject(putObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传文件异常";
        }

        //ResponseParam为自定义返回json格式
        return cosConfig.getCosHost() + "/" + key;
    }
    /**
     * 接口只能接受MultipartFile, 腾讯云需要File
     * 故 MultipartFile => File
     * @param multiFile 上传文件
     * @return file
     */
    public static File MultipartFileToFile(MultipartFile multiFile) throws Exception {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
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


    private JsonResponse<?> fileLimits(int limit, String ipaddr, int uploadTimesInDay) {
        if (uploadTimesInDay >= limit) {
            return JsonResponse.Builder.buildFailure("您今日上传以超过次数限制: " + limit + "次~ 请在 "
                    + TimeUtil.formatDateTime(redisUtil.getExpire(ipaddr)) + " 后重试");
        }
        if (uploadTimesInDay == 0) {
            redisUtil.set(ipaddr);
        } else {
            redisUtil.increment(ipaddr);
        }
        return null;
    }

}




