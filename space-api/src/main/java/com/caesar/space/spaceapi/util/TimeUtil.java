package com.caesar.space.spaceapi.util;

import java.util.Calendar;
import java.util.Date;

/**
 * <h3>TimeUtil</h3>
 * <p>时间工具类</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-23 16:20
 **/
public class TimeUtil {

    /**
     * 计算现在到今天结束还剩多少秒
     *
     * @param currentDate currentDate
     * @return Integer
     */
    public static Long getRemainSecondsOneDay(Date currentDate) {
        Calendar midnight= Calendar.getInstance();
        midnight.setTime(currentDate);
        midnight.add(Calendar.DAY_OF_MONTH,1);
        midnight.set(Calendar.HOUR_OF_DAY,0);
        midnight.set(Calendar.MINUTE,0);
        midnight.set(Calendar.SECOND,0);
        midnight.set(Calendar.MILLISECOND,0);
        return (midnight.getTime().getTime()-currentDate.getTime())/1000;
    }

    /**
     * 将秒转化为 时间格式
     *
     * @param mss mss
     * @return String
     */
    public static String formatDateTime(long mss) {
        String DateTimes;
        long days = mss / ( 60 * 60 * 24);
        long hours = (mss % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % ( 60 * 60)) /60;
        long seconds = mss % 60;
        if(days>0){
            DateTimes= days + "天" + hours + "小时" + minutes + "分钟"
                    + seconds + "秒";
        }else if(hours>0){
            DateTimes=hours + "小时" + minutes + "分钟"
                    + seconds + "秒";
        }else if(minutes>0){
            DateTimes=minutes + "分钟"
                    + seconds + "秒";
        }else{
            DateTimes=seconds + "秒";
        }
        return DateTimes;
    }
}
