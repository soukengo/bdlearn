* 服务器代码存放目录: /home/student5/wusq/ch04

* 作业1：
  1. 进入/home/student5/wusq/ch04，将数据文件放入hdfs文件系统
  ```shell
  hdfs dfs -put /home/student5/wusq/ch04/data/* /user/student5/wusq/ch04/data/
  ```
  2. 执行脚本，提交spark任务

  ```shell
  sh inverted_index.sh
  ```
  3. 执行结果
  
  ![执行结果](work1/img.png)