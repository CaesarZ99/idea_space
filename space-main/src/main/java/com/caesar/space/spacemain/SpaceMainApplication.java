package com.caesar.space.spacemain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication(scanBasePackages = {"com.caesar.space.spacemain", "com.caesar.space.spaceapi"})
@EnableDiscoveryClient
@EnableHystrix
@MapperScan("com.caesar.space.spacemain.mapper")
public class SpaceMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceMainApplication.class, args);
    }


}
