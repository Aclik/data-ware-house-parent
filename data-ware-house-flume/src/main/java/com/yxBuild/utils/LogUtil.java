package com.yxBuild.utils;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 校验日志格式
 * 
 */
public class LogUtil {

    /**
     *  校验启动日志格式
     *
     * @param logContent 日志内容
     */
    public static boolean checkStartLogFormat(String logContent) {
        // 判断内容是否
        if (logContent.startsWith("{") && logContent.endsWith("}")) {
            return true;
        }else {
            return false;
        }
    }

    /**
     *
     *  校验行为日志格式
     *
     * @param logContent
     * @return
     */
    public static boolean checkEventLogFormat(String logContent) {
        // 1、根据"|"进行切割
        String[] splitLog = logContent.split("\\|");

        // 2、判断切割后的长度是否大于等于2
        if (splitLog.length < 2) {
          return false;
        }

        // 3、判断第一个值是否符合时间戳格式
        if (splitLog[0].length() < 13 || !NumberUtils.isDigits(splitLog[0])) {
            return false;
        }

        // 4、判断切割后的第一个值长度是否符合时间戳的长度、第二个值是否符合JSON格式
        if (splitLog[1].trim().startsWith("{") && splitLog[1].trim().endsWith("}")) {
            return true;
        }
        return false;
    }
}
