package com.yxBuild.UDF;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.JSONObject;

/**
 * 自定义UDF函数
 */
public class BaseFieldUDF extends UDF {

    /**
     * 重载evaluate函数
     *
     * @param eventInfoSource 通过SQL传入的字段值
     * @param jsonKeyString 需要获取的Key值
     * @return
     */
    public String evaluate(String eventInfoSource,String jsonKeyString) {
        // 1、创建一个StringBuilder对象
        StringBuilder sb = new StringBuilder();

        // 2、根据逗号分隔需要获取的key值
        String[] jsonKeys = jsonKeyString.split(",");

        // 3、分隔事件信息
        String[] eventInfos = eventInfoSource.split("\\|");

        // 4、去掉字符串,获取用户行为信息JSON对象
        try {
            JSONObject eventInfoJsonObject = new JSONObject(eventInfos[1]);

            // 5、获取共同部分的事件信息
            JSONObject commonJsonObject = eventInfoJsonObject.getJSONObject("cm");

            // 6、根据传入的Key值,获取公共部分事件的具体字段值
            for (String jsonKey : jsonKeys) {
                String value = commonJsonObject.getString(jsonKey);
                if(value == null) {
                    sb.append("\t");
                }else {
                    sb.append(value).append("\t");
                }
            }
            // 7、追加JSON事件数组
            sb.append(eventInfoJsonObject.getString("et")).append("\t");

            // 8、将服务器时间进行追加到最后
            sb.append(eventInfos[0]);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(eventInfos);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String line = "1541217850324|{\"cm\":{\"mid\":\"m7856\",\"uid\":\"u8739\",\"ln\":\"-74.8\",\"sv\":\"V2.2.2\",\"os\":\"8.1.3\",\"g\":\"P7XC9126@gmail.com\",\"nw\":\"3G\",\"l\":\"es\",\"vc\":\"6\",\"hw\":\"640*960\",\"ar\":\"MX\",\"t\":\"1541204134250\",\"la\":\"-31.7\",\"md\":\"huawei-17\",\"vn\":\"1.1.2\",\"sr\":\"O\",\"ba\":\"Huawei\"},\"ap\":\"weather\",\"et\":[{\"ett\":\"1541146624055\",\"en\":\"display\",\"kv\":{\"goodsid\":\"n4195\",\"copyright\":\"ESPN\",\"content_provider\":\"CNN\",\"extend2\":\"5\",\"action\":\"2\",\"extend1\":\"2\",\"place\":\"3\",\"showtype\":\"2\",\"category\":\"72\",\"newstype\":\"5\"}},{\"ett\":\"1541213331817\",\"en\":\"loading\",\"kv\":{\"extend2\":\"\",\"loading_time\":\"15\",\"action\":\"3\",\"extend1\":\"\",\"type1\":\"\",\"type\":\"3\",\"loading_way\":\"1\"}},{\"ett\":\"1541126195645\",\"en\":\"ad\",\"kv\":{\"entry\":\"3\",\"show_style\":\"0\",\"action\":\"2\",\"detail\":\"325\",\"source\":\"4\",\"behavior\":\"2\",\"content\":\"1\",\"newstype\":\"5\"}},{\"ett\":\"1541202678812\",\"en\":\"notification\",\"kv\":{\"ap_time\":\"1541184614380\",\"action\":\"3\",\"type\":\"4\",\"content\":\"\"}},{\"ett\":\"1541194686688\",\"en\":\"active_background\",\"kv\":{\"active_source\":\"3\"}}]}";
        String x = new BaseFieldUDF().evaluate(line, "mid,uid,vc,vn,l,sr,os,ar,md,ba,sv,g,hw,nw,ln,la,t");
        System.out.println(x);

    }

}
