package com.caesar.space.spaceapi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h3>RabbitmqConstant</h3>
 * <p>rabbitmq 常量枚举</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-21 09:47
 **/
@Getter
@AllArgsConstructor
public enum RabbitmqConstant {
    QUEUE_BOOT("QUEUE_BOOT","BOOT队列","topic"),
    EXCHANGE_BOOT("EXCHANGE_BOOT","BOOT交换机","topic"),
    QUEUE_FILE("QUEUE_FILE","FILE队列","topic"),
    EXCHANGE_FILE("EXCHANGE_FILE","FILE交换机","topic");
    private final String code;
    private final String name;
    private final String type;

}
