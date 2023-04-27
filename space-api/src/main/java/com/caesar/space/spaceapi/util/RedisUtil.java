package com.caesar.space.spaceapi.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <h3>RedisTool</h3>
 * <p>redis工具类</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-17 14:54
 **/
@Component
public class RedisUtil {

    public static final String FILE_UPLOAD_PREFIX = "BasicController:uploadFile:";
    @Resource
    StringRedisTemplate stringRedisTemplate;

    public void set(String ipaddr) {
        // 当前key不存在，写入值, 并返回true; 当前key已经存在，不处理, 返回false;  Absent: 缺少的，
        stringRedisTemplate.opsForValue().set(FILE_UPLOAD_PREFIX + ipaddr, "1", TimeUtil.getRemainSecondsOneDay(new Date()), TimeUnit.SECONDS);
    }

    public Boolean setIfAbsent(String ipaddr) {
        // 当前key不存在，写入值, 并返回true; 当前key已经存在，不处理, 返回false;  Absent: 缺少的，
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(FILE_UPLOAD_PREFIX + ipaddr, "1", 1, TimeUnit.DAYS));
    }

    public Boolean setIfPresent(String ipaddr, String value) {
        // 当前key已经存在，写入值, 并返回true; 当前key不存在，不处理, 返回false;  ;Present: 存在的
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfPresent(FILE_UPLOAD_PREFIX + ipaddr, value, 1, TimeUnit.DAYS));
    }

    public Integer get(String ipaddr) {
        String value = stringRedisTemplate.opsForValue().get(FILE_UPLOAD_PREFIX + ipaddr);
        return value == null ? 0 : Integer.parseInt(value);
    }

    public Long increment(String ipaddr) {
        return stringRedisTemplate.opsForValue().increment(FILE_UPLOAD_PREFIX + ipaddr);
    }

    public Long getExpire(String ipaddr) {
        return stringRedisTemplate.opsForValue().getOperations().getExpire(FILE_UPLOAD_PREFIX + ipaddr);
    }

}
