package com.caesar.space.spaceapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h3>LogUtil</h3>
 * <p>日志工具类</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-27 13:56
 **/
public class LogUtil {

    private static Logger LOGGER = null;

    static void initLogger(Logger logger) {
        LOGGER = logger;
    }

    public static <T> void logInfo(String info, Class<T> clazz) {
        initLogger(LoggerFactory.getLogger(clazz));
        LOGGER.info(info);
    }

    public static <T> void logError(String info, Class<T> clazz) {
        initLogger(LoggerFactory.getLogger(clazz));
        LOGGER.error(info);
    }

}
