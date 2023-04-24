package com.caesar.space.spacefile;

import com.caesar.space.spacefile.config.COSConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.caesar.space.spacefile","com.caesar.space.spaceapi"})
@MapperScan("com.caesar.space.spacefile.mapper")
public class SpaceFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceFileApplication.class, args);
    }

    @Autowired
    COSConfig cosConfig;
    @Bean
    /**
     * 获取腾讯云COS客户端
     * @return COSClient
     */
    public COSClient initCosClient() {
        COSCredentials cred = new BasicCOSCredentials(cosConfig.getAccessKey(), cosConfig.getSecretKey());
        Region region = new Region(cosConfig.getRegionName());
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }
}
