package com.caesar.space.spacemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caesar.space.spaceapi.constant.RedisKeyConstant;
import com.caesar.space.spaceapi.constant.ServiceConstant;
import com.caesar.space.spaceapi.exception.ServiceException;
import com.caesar.space.spaceapi.responce.JsonResponse;
import com.caesar.space.spaceapi.util.IpUtil;
import com.caesar.space.spaceapi.util.LogUtil;
import com.caesar.space.spaceapi.util.MqUtil;
import com.caesar.space.spaceapi.domain.User;
import com.caesar.space.spacemain.mapper.BasicUserMapper;
import com.caesar.space.spacemain.service.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <h3>BasicUserServiceImpl</h3>
 * <p>基础用户接口的实现类</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-20 17:11
 **/
@Service
public class BasicUserServiceImpl extends ServiceImpl<BasicUserMapper, User> implements BasicUserService {

    @Autowired
    private BasicUserMapper basicUserMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MqUtil mqUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public User gerSingleUserInfo(Long userId) {
        return basicUserMapper.selectUserById(userId);
    }

    @Override
    public Object uploadFileBySpaceFile(MultipartFile file, String captcha, HttpServletRequest request) throws InterruptedException {
        String fileUploadCaptcha = redisTemplate.opsForValue().get(RedisKeyConstant.FILE_UPLOAD_CAPTCHA_PREFIX.getCode() + IpUtil.getIpAddr(request));
        if (StringUtils.isEmpty(fileUploadCaptcha)
            || !captcha.equals(fileUploadCaptcha)
        ) {
            return JsonResponse.Builder.buildFailure("验证码不正确哦");
        }
        LogUtil.logInfo("验证码校验通过了",this.getClass());
        // 文件 MultiValueMap传参
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        // 通过file.getResource()传输文件
        formData.add("file", file.getResource());
        formData.add("ipAddr", IpUtil.getIpAddr(request));
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "multipart/form-data");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);
        ResponseEntity<String> stringResponseEntity;
        try {
            // 向space-file服务上传接口发送上传请求
            stringResponseEntity = restTemplate.postForEntity(ServiceConstant.IDEA_SPACE_FILE.getCode() + "/file/upload", requestEntity, String.class);
        } catch (RestClientException e) {
            // 文件服务异常, 使用自定义异常输出信息
            throw new ServiceException("上传文件 --- 调用" + ServiceConstant.IDEA_SPACE_FILE.getName() + "异常");
        } finally {
            Boolean delete = redisTemplate.delete(RedisKeyConstant.FILE_UPLOAD_PREFIX.getCode() + captcha);
            int count = 0;
            while (Boolean.FALSE.equals(delete)) {
                Thread.sleep(1000);
                delete = redisTemplate.delete(RedisKeyConstant.FILE_UPLOAD_PREFIX.getCode() + captcha);
                count++;
                // 失败重试,十次之后放弃重试
                if (count >= 10) {
                    break;
                }
            }
        }

        // 获取响应body
        return stringResponseEntity.getBody();
    }

    public boolean checkValidUserId(Long userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        User user = basicUserMapper.selectOne(wrapper);
        return user == null;
    }

    @Override
    public void uploadFileByMq(MultipartFile file) {
        mqUtil.sendSignUpMailCode(file);
    }
}
