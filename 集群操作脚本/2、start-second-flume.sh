#! /bin/bash

# 判断用户是否输入参数
paramNu=$#
if((paramNu <= 0))
   then
     echo '请输入参数:';
     echo '  start[启动Flume采集数据]';
     echo '  stop [关闭Flume采集数据]';
     exit;
fi


# 启动第二层Flume进行日志数据采集

case $1 in
"start"){
        for i in hadoop103
        do
                echo " --------启动 $i 采集flume-------"
                ssh $i "nohup /opt/module/flume-1.7.0/bin/flume-ng agent -c /opt/module/flume-1.7.0/conf -f /opt/project/data-ware-house/agent/agent-kafka-file-hdfs.conf -n a1 -Dflume.root.logger=INFO,LOGFILE >/dev/null 2>&1 &"
        done
};;
"stop"){
        for i in hadoop102 hadoop103
        do
                echo " --------停止 $i 采集flume-------"
                ssh $i "ps -ef | grep agent-kafka-file-hdfs | grep -v grep |awk '{print \$2}' | xargs kill"
        done

};;
esac
