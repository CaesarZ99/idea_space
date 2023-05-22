package com.caesar.space.spacemain.service.impl;

import com.caesar.space.spaceapi.constant.RedisKeyConstant;
import com.caesar.space.spaceapi.util.CaptchaGenerator;
import com.caesar.space.spaceapi.util.IpUtil;
import com.caesar.space.spacemain.service.CaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * <h3>CaptchaServiceImpl</h3>
 * <p>验证码服务实现类</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-05-21 19:22
 **/
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public BufferedImage getCachedCaptcha(HttpServletRequest request) {
        BufferedImage bufferedImage = CaptchaGenerator.generateCaptchaImage();
        String string = CaptchaGenerator.captchaCode.toString().toLowerCase();
        // 缓存验证码五分钟
        redisTemplate.opsForValue().set(RedisKeyConstant.FILE_UPLOAD_CAPTCHA_PREFIX.getCode() + IpUtil.getIpAddr(request), string, 300L, TimeUnit.SECONDS);
        // 某ip请求达到次数限制，五分钟后重试
        String limit = RedisKeyConstant.FILE_UPLOAD_CAPTCHA_LIMIT_PREFIX.getCode() + IpUtil.getIpAddr(request);
        redisTemplate.opsForValue().setIfAbsent(limit,"0", 300, TimeUnit.SECONDS);
        redisTemplate.opsForValue().increment(limit);
        return bufferedImage;
    }

    @Override
    public boolean captchaLimit(HttpServletRequest request) {
        String limit = redisTemplate.opsForValue().get(RedisKeyConstant.FILE_UPLOAD_CAPTCHA_LIMIT_PREFIX.getCode() + IpUtil.getIpAddr(request));
        return StringUtils.isNotEmpty(limit) && Integer.parseInt(limit) >= 10;
    }
}
