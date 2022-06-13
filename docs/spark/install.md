## macbook m1 pro安装 spark集群
### 1.准备工作
* 复用安装好的hadoop集群环境 => [hadoop集群安装](../hadoop/install.md)
* 规划workers节点: bd01,bd02,bd03
* 其中bd01为master节点

### 2.安装spark
* 下载安装（3台服务器都执行）
```
cd /usr/local/sbin
wget https://dlcdn.apache.org/spark/spark-3.2.1/spark-3.2.1-bin-hadoop3.2.tgz

tar -zxvf spark-3.2.1-bin-hadoop3.2.tgz
```
* 配置系统环境变量
  * 修改/etc/profile
  * 增加SPARK_HOME
  ```shell
   export SPARK_HOME=/usr/local/sbin/spark-3.2.1-bin-hadoop3.2
   export PATH=$PATH:$SPARK_HOME/bin
  ```
* 修改spark环境变量
  * 复制conf/spark-env.sh.template 为conf/spark-env.sh
  * 增加内容
  
  ```shell
    export JAVA_HOME=/usr/local/sbin/jdk1.8.0_333
    export HADOOP_HOME=/usr/local/sbin/hadoop-3.3.1
    export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
    export SPARK_MASTER_HOST=bd01
  ```
    
* 配置workers
  * 复制conf/workers.template 为conf/workers
    * 增加内容
    
    ```shell
      bd01
      bd02
      bd03
    ```
* 开启jobhistory（用于查看作业执行记录）
  * 复制conf/spark-defaults.conf.template 为 conf/spark-defaults.conf
  * 增加内容
  ```shell
    spark.history.ui.port            18080
    spark.history.retainedApplications  10
    spark.eventLog.compress          true
    spark.eventLog.enabled           true
    spark.eventLog.dir               hdfs://bd01:8020/user/spark/history
    spark.history.fs.logDirectory    hdfs://bd01:8020/user/spark/history
  ```
  
* 将conf/spark-defaults.conf, conf/spark-env.sh ,conf/workers 3个文件复制到另外两台机器对应目录下
* 启动 spark集群
  * 在master节点执行
      ```shell
        sh sbin/start-all.sh
      ```
  * 访问： http://bd01:8080  
* 启动jobhistory server
  * 在master节点执行
    ```shell
      sh sbin/start-history-server.sh 
    ```
  * 访问  http://bd01:18080

