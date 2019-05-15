package com.yxBuild.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 日志类型拦截器(根据启动日志和行为日志进行添加Header值,从而设置Kafka Channel进行对应的Topic进行生产数据)
 *
 */
public class LogTypeInterceptor implements Interceptor {
    /**
     *  初始化(只调用一次)
     */
    @Override
    public void initialize() {

    }

    /**
     * 处理单个Event
     *
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {
        // 1、获取Event头部信息
        Map<String, String> headers = event.getHeaders();

        // 2、获取日志内容信息
        String logContent = new String(event.getBody());

        // 3、判断日志类型
        if (logContent.contains("start")) {
            // 启动日志
            headers.put("topic","topic_start");
        }else {
            // 行为日志
            headers.put("topic","topic_event");
        }
        return event;
    }

    /**
     * 处理多个Event
     *
     * @param events Event对象的List集合
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> events) {
        List<Event> interceptors = new ArrayList<>();
        for (Event event : events) {
            Event intercept = intercept(event);
            interceptors.add(intercept);
        }
        return interceptors;
    }

    /**
     * 关闭资源(只调用一次)
     *
     */
    @Override
    public void close() {

    }

    /**
     * 创建内部类Builder
     *
     */
    public static class Builder implements Interceptor.Builder{
        @Override
        public Interceptor build() {
            return new LogTypeInterceptor();
        }
        @Override
        public void configure(Context context) {

        }
    }
}
