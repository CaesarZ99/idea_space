package com.caesar.space.spaceapi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceConstant {
    IDEA_SPACE_FILE("http://space-file","文件服务"),
    HTTP("http://","http协议"),
    HTTPS("https://","https协议");

    private final String code;
    private final String name;
}
