#!/bin/bash

# 判断用户是否输入参数
paramNu=$#
if((paramNu <= 0))
   then
     echo '请输入参数:';
     echo '  需要加载数据的日期,格式:yyyy-MM-dd';
     exit;
fi

# 加载ods层的数据

APP=gmall2 # 数据库名称
hive=/opt/module/hive-1.2.1/bin/hive # Hive执行命令

# 如果是输入的日期按照取输入日期；如果没输入日期取当前时间的前一天
if [ -n "$1" ] ;then
 do_date=$1
else
 do_date=`date -d "-1 day" +%F`
fi

echo "===日志日期为 $do_date==="
sql="
load data inpath '/project/data-ware-house/origin_data/gmall/log/topic_start/$do_date' into table "$APP".ods_start_log partition(dt='$do_date');

load data inpath '/project/data-ware-house/origin_data/gmall/log/topic_event/$do_date' into table "$APP".ods_event_log partition(dt='$do_date');
"

$hive -e "$sql"
