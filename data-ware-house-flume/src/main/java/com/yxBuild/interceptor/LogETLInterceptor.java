package com.yxBuild.interceptor;

import com.yxBuild.utils.LogUtil;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Flume的日志清洗过滤器
 *
 *
 */
public class LogETLInterceptor implements Interceptor {

    /**
     * 初始化(只调用一次)
     *
     */
    public void initialize() {

    }

    /**
     * 处理单个Event
     *
     * @param event
     * @return
     */
    public Event intercept(Event event) {
        boolean isCheckSuccess;

        // 1、获取接收到的内容
        String logContent = new String(event.getBody());

        // 2、判断日志类型(1.启动日志 2.行为日志)
        if (logContent.contains("start")) {
            // 3、启动日志,对日志格式进行校验
            isCheckSuccess = LogUtil.checkStartLogFormat(logContent);

        }else {
            // 4、行为日志,对日志格式进行校验
            isCheckSuccess = LogUtil.checkEventLogFormat(logContent);
        }

        // 5、判断日志格式是否校验成功,如果校验成功,直接返回
        if (isCheckSuccess) {
            return event;
        }else {
            return null;
        }
    }

    /**
     * 处理多个Event
     *
     * @param list Event对象的List集合
     * @return
     */
    public List<Event> intercept(List<Event> list) {
        List<Event> resultEventList = new ArrayList<>();
        for (Event event : list) {
            Event intercept = intercept(event);
            if (intercept != null) {
                resultEventList.add(intercept);
            }
        }
        return resultEventList;
    }

    /**
     * 关闭资源(只调用一次)
     */
    public void close() {

    }
}
