package com.caesar.space.spacefile.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "tencent.cos")
public class COSConfig {
    private String baseUrl;
    private String accessKey;
    private String secretKey;
    private String regionName;
    private String bucketName;
    private String folderPrefix;
    private String cosHost;

    @Override
    public String toString() {
        return "COSConfig{" +
                "baseUrl='" + baseUrl + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", regionName='" + regionName + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", folderPrefix='" + folderPrefix + '\'' +
                ", cosHost='" + cosHost + '\'' +
                '}';
    }
}