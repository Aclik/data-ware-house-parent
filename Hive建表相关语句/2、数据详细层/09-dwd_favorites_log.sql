-- 收藏日志信息表
CREATE EXTERNAL TABLE dwd_favorites_log(
`mid_id` string,
`user_id` string,
`version_code` string,
`version_name` string,
`lang` string,
`source` string,
`os` string,
`area` string,
`model` string,
`brand` string,
`sdk_version` string,
`gmail` string,
`height_width` string,
`app_time` string,
`network` string,
`lng` string,
`lat` string,
`id` int,
`course_id` int,
`userid` int,
`add_time` string,
`server_time` string
)
PARTITIONED BY (dt string)
location '/project/data-ware-house/origin_data/gmall/hive/dwd//dwd_favorites_log/';
