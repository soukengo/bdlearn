## 第三周作业

### 1 准备工作

#### 1.1 创建数据库(v1)
* 依次执行[sql脚本](sql/ddl.sql)
* 手动创建表存储目录
```shell
# 用户表
hdfs dfs -mkdir -p /user/student5/wusq/ch03/data/users
# 电影表
hdfs dfs -mkdir -p /user/student5/wusq/ch03/data/movies
# 评分表
hdfs dfs -mkdir -p /user/student5/wusq/ch03/data/ratings
```
* 手动put数据
```shell
hdfs dfs -put /home/student5/wusq/ch03/data/users.dat /user/student5/wusq/ch03/data/users
hdfs dfs -put /home/student5/wusq/ch03/data/movies.dat /user/student5/wusq/ch03/data/movies
hdfs dfs -put /home/student5/wusq/ch03/data/ratings.dat /user/student5/wusq/ch03/data/ratings
```

#### 1.2 创建数据库(V2)
* 通过beeline连接hive
* 依次执行[sql脚本](sql/ddl_v2.sql)

### 2 作业实现

### 2.1 作业1
* SQL
```sql
SELECT 
    u.age,AVG(r.rate) avgrate
FROM wusq.t_movie m
JOIN wusq.t_rating r ON r.movieid = m.movieid
JOIN wusq.t_user u ON u.userid = r.userid
WHERE m.movieid = 2116
GROUP BY u.age;
```

### 2.2 作业2
* SQL

```sql
SELECT 
    u.sex,m.moviename name,AVG(r.rate) avgrate,COUNT(1) total
FROM wusq.t_movie m
JOIN wusq.t_rating r ON r.movieid = m.movieid
JOIN wusq.t_user u ON u.userid = r.userid
WHERE u.sex = 'M'
GROUP BY u.sex,m.moviename
HAVING total > 50
ORDER BY avgrate DESC
LIMIT 10;
```

### 2.3 作业3
* SQL
```sql
-- step3 计算平均分
SELECT m.moviename,avg(r2.rate) avgrate
FROM (
    -- step2 计算评分最多用户所给出最高分10部电影id
	SELECT r.movieid 
	FROM (
	    -- step1 计算 评分次数最多的用户id
		SELECT u.userid,COUNT(1) max_count
		FROM t_rating r
		JOIN t_user u ON u.userid=r.userid
		WHERE u.sex='F'
		GROUP BY u.userid
		ORDER BY max_count
		DESC LIMIT 1
	) mc 
    JOIN t_rating r ON mc.userid=r.userid
    ORDER BY r.rate DESC LIMIT 10
) mr
JOIN t_rating r2 ON mr.movieid=r2.movieid
JOIN t_movie m ON m.movieid=mr.movieid
GROUP BY m.moviename;
```