package com.yxBuild.UDTF;


import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析用户行为事件的一进多出函数定义
 *
 */
public class EventJsonUDTF extends GenericUDTF {

    /**
     * 初始化返回值的名称和数据类型,个数需要和process中的forward返回的个数一致
     *
     * @param argOIs
     * @return
     * @throws UDFArgumentException
     */
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        List<String> structFieldNames = new ArrayList<>(); // 返回值的名称集
        List<ObjectInspector> structFieldObjectInspectors = new ArrayList<>();// 返回值的数据类型集

        structFieldNames.add("event_name"); // 返回值名称为"event_name"
        structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);// 类型为字符串类型

        structFieldNames.add("event_json"); // 返回值名称为"event_json"
        structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);// 类型为字符串类型

        return ObjectInspectorFactory.getStandardStructObjectInspector(structFieldNames, structFieldObjectInspectors);
    }

    /**
     * 逻辑业务处理方法
     *
     * @param objects 所传入的数据集
     * @throws HiveException
     */
    @Override
    public void process(Object[] objects) throws HiveException {
        // 1、获取传入用户行为的数据
        String input = objects[0].toString();

        try {
            // 2、将用户行为数据转换为JSON数组对象
            JSONArray jsonArray = new JSONArray(input);

            // 3、遍历用户行为信息
            for (int i = 0; i < jsonArray.length(); i++) {
                // 4、获取用户行为信息
                JSONObject eventJSONObject = jsonArray.getJSONObject(i);
                // 5、获取事件名称
                String eventName = eventJSONObject.getString("en");
                // 6、定义保存返回值的存储遍历
                String[] backValueArray = new String[2];
                // 7、将返回值存储到变量
                backValueArray[0] = eventName;
                backValueArray[1] = eventJSONObject.toString();
                // 8、调用父类方法,将数据写出,并缓存
                forward(backValueArray);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源方法
     *
     * @throws HiveException
     */
    @Override
    public void close() throws HiveException {

    }

    public static void main(String[] args) {
        String line[] = new String[1];
        line[0] = ("[{\"ett\":\"1541146624055\",\"en\":\"display\",\"kv\":{\"copyright\":\"ESPN\",\"content_provider\":\"CNN\",\"extend2\":\"5\",\"goodsid\":\"n4195\",\"action\":\"2\",\"extend1\":\"2\",\"place\":\"3\",\"showtype\":\"2\",\"category\":\"72\",\"newstype\":\"5\"}},{\"ett\":\"1541213331817\",\"en\":\"loading\",\"kv\":{\"extend2\":\"\",\"loading_time\":\"15\",\"action\":\"3\",\"extend1\":\"\",\"type1\":\"\",\"type\":\"3\",\"loading_way\":\"1\"}},{\"ett\":\"1541126195645\",\"en\":\"ad\",\"kv\":{\"entry\":\"3\",\"show_style\":\"0\",\"action\":\"2\",\"detail\":\"325\",\"source\":\"4\",\"behavior\":\"2\",\"content\":\"1\",\"newstype\":\"5\"}},{\"ett\":\"1541202678812\",\"en\":\"notification\",\"kv\":{\"ap_time\":\"1541184614380\",\"action\":\"3\",\"type\":\"4\",\"content\":\"\"}},{\"ett\":\"1541194686688\",\"en\":\"active_background\",\"kv\":{\"active_source\":\"3\"}}]");
        EventJsonUDTF eventJsonUDTF = new EventJsonUDTF();
        try {
            eventJsonUDTF.process(line);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
