package com.yxBuild;

import com.yxBuild.interceptor.LogETLInterceptor;
import org.apache.flume.Event;
import org.apache.flume.event.SimpleEvent;

/**
 * 测试日志清洗拦截器主方法
 *
 */
public class LogETLInterceptorTestApp {
    public static void main(String[] args) {
        // 1、创建自定义拦截器实例对象
        LogETLInterceptor logETLInterceptor = new LogETLInterceptor();

        // 2、创建事件实例对象
        Event event = new SimpleEvent();

        // 3、给事件的Body添加内容
        // event.setBody("{start}".getBytes()); // 启动日志格式测试
        event.setBody("1557883491170|{\"cm\":{\"ln\":\"-87.0\",\"sv\":\"V2.5.4\"}".getBytes()); // 行为日志格式测试

        // 4、传输event进行校验
        Event intercept = logETLInterceptor.intercept(event);
        System.out.println(intercept);
    }
}
