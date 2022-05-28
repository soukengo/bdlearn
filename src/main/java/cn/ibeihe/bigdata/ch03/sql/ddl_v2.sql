-- 创建数据库
create database if not exists wusq;
-- 创建观众表

create external table if not exists wusq.t_user
(
    userid string,
    sex string,
    age int,
    occupation string,
    zipcode string
) COMMENT '观众表'
ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe'
WITH SERDEPROPERTIES ("field.delim"="::")
LOCATION '/user/student5/wusq/ch03/data1/users'
;

-- 创建电影表
create external table if not exists wusq.t_movie
(
    movieid string,
    moviename string,
    movietype string
) COMMENT '电影表'
ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe'
WITH SERDEPROPERTIES ("field.delim"="::")
LOCATION '/user/student5/wusq/ch03/data1/movies'
;

-- 创建评分表
create external table if not exists wusq.t_rating
(
    userid string,
    movieid string,
    rate double,
    times bigint
) COMMENT '评分表'
ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe'
WITH SERDEPROPERTIES ("field.delim"="::")
LOCATION '/user/student5/wusq/ch03/data1/ratings'
;

load data inpath '/user/student5/wusq/ch03/tmp/users.dat' into table wusq.t_user;
load data inpath '/user/student5/wusq/ch03/tmp/movies.dat' into table wusq.t_movie;
load data inpath '/user/student5/wusq/ch03/tmp/ratings.dat' into table wusq.t_rating;