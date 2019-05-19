create external table ods_start_log(
line string
)
partitioned by (dt string)
stored as
  inputformat 'com.hadoop.mapred.DeprecatedLzoTextInputFormat'
  outputformat 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
location '/project/data-ware-house/origin_data/gmall/hive/ods/ods_start_log';