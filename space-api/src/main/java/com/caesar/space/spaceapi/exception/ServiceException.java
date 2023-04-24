package com.caesar.space.spaceapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <h3>ServiceException</h3>
 * <p>自定义异常</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-24 14:43
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException {
    private int code=500;
    private String msg;


    //对该异常类的构造方法进行补充，不写的化会默认只有一个无参构造
    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServiceException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ServiceException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ServiceException(int code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
