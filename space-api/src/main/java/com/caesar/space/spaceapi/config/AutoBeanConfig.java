package com.caesar.space.spaceapi.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <h3>AutoBeanConfig</h3>
 * <p>加载一些主要的bean</p>
 *
 * @author Caesar·Liu
 * @date 2023-04-23 13:34
 **/
@Configuration
public class AutoBeanConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder build) {
        return build.build();
    }
}
