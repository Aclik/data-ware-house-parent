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
        event.setBody("1557883491170|{\"cm\":{\"ln\":\"-87.0\",\"sv\":\"V2.5.4\",\"os\":\"8.2.5\",\"g\":\"3D006LB8@gmail.com\",\"mid\":\"4\",\"nw\":\"3G\",\"l\":\"pt\",\"vc\":\"9\",\"hw\":\"640*1136\",\"ar\":\"MX\",\"uid\":\"4\",\"t\":\"1557877131404\",\"la\":\"-31.0\",\"md\":\"Huawei-2\",\"vn\":\"1.3.9\",\"ba\":\"Huawei\",\"sr\":\"W\"},\"ap\":\"gmall\",\"et\":[{\"ett\":\"1557863946661\",\"en\":\"display\",\"kv\":{\"goodsid\":\"0\",\"action\":\"1\",\"extend1\":\"2\",\"place\":\"1\",\"category\":\"22\"}},{\"ett\":\"1557841933881\",\"en\":\"ad\",\"kv\":{\"entry\":\"3\",\"show_style\":\"5\",\"action\":\"3\",\"detail\":\"\",\"source\":\"1\",\"behavior\":\"2\",\"content\":\"2\",\"newsType\":\"0\"}},{\"ett\":\"1557825802085\",\"en\":\"active_background\",\"kv\":{\"active_source\":\"2\"}},{\"ett\":\"1557830544149\",\"en\":\"error\",\"kv\":{\"errorDetail\":\"at cn.lift.dfdfdf.control.CommandUtil.getInfo(CommandUtil.java:67)\\\\n at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\\\\n at java.lang.reflect.Method.invoke(Method.java:606)\\\\n\",\"errorBrief\":\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\"}},{\"ett\":\"1557797480717\",\"en\":\"praise\",\"kv\":{\"target_id\":3,\"id\":6,\"type\":4,\"add_time\":\"1557846897459\",\"userid\":9}}]}".getBytes()); // 行为日志格式测试

        // 4、传输event进行校验
        Event intercept = logETLInterceptor.intercept(event);
        System.out.println(intercept);
    }
}
