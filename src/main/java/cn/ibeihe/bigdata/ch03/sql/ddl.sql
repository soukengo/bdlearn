-- 创建数据库
create database if not exists wusq;
-- 创建观众表
create external table if not exists wusq.ch03_user
(
    userid string,
    sex string,
    age int,
    occupation string,
    zipcode string
) COMMENT '观众表'
ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe'
WITH SERDEPROPERTIES ("field.delim"="::")
LOCATION '/user/student5/wusq/ch03/data/users'
;

-- 创建电影表
create external table if not exists wusq.ch03_movie
(
    movieid string,
    moviename string,
    movietype string
) COMMENT '电影表'
ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe'
WITH SERDEPROPERTIES ("field.delim"="::")
LOCATION '/user/student5/wusq/ch03/data/movies'
;

-- 创建评分表
create external table if not exists wusq.ch03_rating
(
    userid string,
    movieid string,
    rate double,
    times bigint
) COMMENT '评分表'
ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe'
WITH SERDEPROPERTIES ("field.delim"="::")
LOCATION '/user/student5/wusq/ch03/data/ratings'
;