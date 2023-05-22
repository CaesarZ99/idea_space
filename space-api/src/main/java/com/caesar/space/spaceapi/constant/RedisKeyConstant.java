package com.caesar.space.spaceapi.constant;

import lombok.Data;
import lombok.Getter;

@Getter
public enum RedisKeyConstant {
    FILE_UPLOAD_PREFIX("BasicController:uploadFile:","文件上传前缀"),
    FILE_UPLOAD_CAPTCHA_LIMIT_PREFIX("BasicController:uploadFile:captcha:limit:","文件上传验证码次数限制前缀"),
    FILE_UPLOAD_CAPTCHA_PREFIX("BasicController:uploadFile:captcha:","文件上传验证码前缀");

    public final String code;
    public final String desc;

    RedisKeyConstant(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
