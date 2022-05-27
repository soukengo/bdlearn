## 第三周作业

### 1 准备工作
### 1.1 上传数据文件
```shell
# 用户表
hdfs dfs -mkdir -p /user/student5/wusq/ch03/data/users
hdfs dfs -put /home/student5/wusq/ch03/data/users.dat /user/student5/wusq/ch03/data/users
# 电影表
hdfs dfs -mkdir -p /user/student5/wusq/ch03/data/movies
hdfs dfs -put /home/student5/wusq/ch03/data/movies.dat /user/student5/wusq/ch03/data/movies
# 评分表
hdfs dfs -mkdir -p /user/student5/wusq/ch03/data/ratings
hdfs dfs -put /home/student5/wusq/ch03/data/ratings.dat /user/student5/wusq/ch03/data/ratings
```
#### 1.2 创建数据库
* 依次执行[sql脚本](sql/ddl.sql)


### 2 作业实现

### 2.1 作业1
* SQL
```sql
SELECT 
    u.age,AVG(r.rate) avgrate
FROM wusq.ch03_movie m
JOIN wusq.ch03_rating r ON r.movieid = m.movieid
JOIN wusq.ch03_user u ON u.userid = r.userid
GROUP BY u.age
```

### 2.2 作业2
todo

### 2.3 作业3

todo