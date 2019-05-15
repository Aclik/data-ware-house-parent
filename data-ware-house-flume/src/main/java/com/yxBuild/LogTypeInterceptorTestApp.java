package com.yxBuild;

import com.yxBuild.interceptor.LogETLInterceptor;
import com.yxBuild.interceptor.LogTypeInterceptor;
import org.apache.flume.Event;
import org.apache.flume.event.SimpleEvent;

/**
 * 日志类型拦截器测试
 *
 */
public class LogTypeInterceptorTestApp {
    public static void main(String[] args) {
        System.out.println("=========清洗日志=========");
        // 1、创建自定义拦截器实例对象
        LogETLInterceptor logETLInterceptor = new LogETLInterceptor();

        // 2、创建事件实例对象
        Event event = new SimpleEvent();

        // 3、给事件的Body添加内容
        // event.setBody("{start}".getBytes()); // 启动日志格式测试
        event.setBody("1557883491170|{\"cm\":{\"ln\":\"-87.0\",\"sv\":\"V2.5.4\"}".getBytes()); // 行为日志格式测试

        // 4、传输event进行校验
        Event etlEvent = logETLInterceptor.intercept(event);
        System.out.println("=========清洗日志=========");

        // 1、创建自定义拦截器的实例对象
        LogTypeInterceptor LogTypeInterceptor = new LogTypeInterceptor();

        // 2、将清洗日志的数据进行类型拦截器进行设置Event头部信息
        Event typeEvent = LogTypeInterceptor.intercept(etlEvent);

        // 3、打印
        System.out.println(typeEvent);
    }
}
