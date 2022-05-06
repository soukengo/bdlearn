## macbook m1 pro安装 hadoop集群

### 1.准备3台虚拟机 (parallels)
* bd01(NameNode): 10.211.55.3
* bd02(SecondaryNameNode): 10.211.55.4
* bd03(ResourceManager): 10.211.55.5
* 注意修改/etc/hostname
* 注意配置 /etc/hosts
    * 10.211.55.3 bd01
    * 10.211.55.4 bd02
    * 10.211.55.5 bd03

### 2.安装JDK
* 下载 jdk-8u333-linux-aarch64.tar.gz 到/usr/local/sbin/
* 解压
```
cd /usr/local/sbin/
tar -zxvf jdk-8u331-linux-aarch64.tar.gz

```
* 配置环境变量
```
vim /etc/profile
export JAVA_HOME=/usr/local/sbin/jdk1.8.0_333
export PATH=$PATH:$JAVA_HOME/bin
```

### 3.安装HADOOP
* 关闭防火墙
```
sudo ufw disable
```

* 安装hadoop
```
cd /usr/local/sbin
wget https://dlcdn.apache.org/hadoop/common/hadoop-3.3.1/hadoop-3.3.1-aarch64.tar.gz

tar -zxvf hadoop-3.3.1-aarch64.tar.gz
```
* 配置环境变量
```
vim /etc/profile
export JAVA_LIBRARY_PATH=/usr/local/sbin/hadoop/lib/native
export HADOOP_HOME=/usr/local/sbin/hadoop-3.3.2
export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin
```
* 修改配置文件
    * hdfs-site.xml
    ```
    <configuration>
    <!-- nn web端访问地址-->
    <property>
        <name>dfs.namenode.http-address</name>
        <value>bd01:9870</value>
    </property>
    
    <!-- 2nn web端访问地址-->
    <property>
        <name>dfs.namenode.secondary.http-address</name>
        <value>bd02:9868</value>
    </property>
    </configuration>
    ```
    * mapred-site.xml
    ```
    <configuration>
        <!-- 指定MapReduce程序运行在Yarn上 -->
        <property>
            <name>mapreduce.framework.name</name>
            <value>yarn</value>
        </property>
    
        <!-- 历史服务器端地址 -->
        <property>
            <name>mapreduce.jobhistory.address</name>
            <value>bd01:10020</value>
        </property>
    
        <!-- 历史服务器web端地址 -->
        <property>
            <name>mapreduce.jobhistory.webapp.address</name>
            <value>bd01:19888</value>
        </property>
    </configuration>
    ```
    * yarn-site.xml
    ```
    <configuration>
        <!-- Site specific YARN configuration properties -->
        <!-- 指定MR走shuffle -->
        <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
        </property>
    
    
        <!-- 指定ResourceManager的地址-->
        <property>
            <name>yarn.resourcemanager.hostname</name>
            <value>bd03</value>
        </property>
    
        <!-- 环境变量的继承 -->
        <property>
            <name>yarn.nodemanager.env-whitelist</name>
            <value>
                JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME
            </value>
        </property>
    
        <!-- 开启日志聚集功能 -->
        <property>
            <name>yarn.log-aggregation-enable</name>
            <value>true</value>
        </property>
    
        <!-- 设置日志聚集服务器地址-->
        <property>
            <name>yarn.log.server.url</name>
            <value>http://bd01:19888/jobhistory/logs</value>
        </property>
    
        <!-- 设置日志保留时间为7天-->
        <property>
            <name>yarn.log-aggregation.retain-seconds</name>
            <value>604800</value>
        </property>
    
    </configuration>
    ```
    * core-site.xml
    ```
    <configuration>
        <!-- 指定NameNode的地址 -->
        <property>
            <name>fs.defaultFS</name>
            <value>hdfs://bd01:8020</value>
        </property>
        <!-- 指定hadoop数据的存储目录 -->
        <property>
            <name>hadoop.tmp.dir</name>
            <value>/data/hadoop/data</value>
        </property>
    
        <!-- 配置HDFS网页登录使用的静态用户为soukengo（自己的用户名）-->
        <property>
            <name>hadoop.http.staticuser.user</name>
            <value>soukengo</value>
        </property>
    
    </configuration>
    ```
* 配置工作节点
```
vim etc/hadoop/workers
bd01
bd02
bd03
```
* 配置免密钥登录
```
ssh-keygen -t rsa
将生成的id_rsa.pub分别加入到3台机器的authorized_keys文件中

将生成的id_rsa文件拷贝到另外2台机器的.ssh目录下
```

### 4.启动hadoop机群
* 第一次运行需要格式化
```
hdfs namenode -format
```
* 在bd01机器上hadoop目录下执行 sbin/start-dfs.sh
* 出现错误
```
Starting namenodes on [bd01]
ERROR: Attempting to operate on hdfs namenode as root
ERROR: but there is no HDFS_NAMENODE_USER defined. Aborting operation.
Starting datanodes
ERROR: Attempting to operate on hdfs datanode as root
ERROR: but there is no HDFS_DATANODE_USER defined. Aborting operation.
Starting secondary namenodes [bd02]
ERROR: Attempting to operate on hdfs secondarynamenode as root
ERROR: but there is no HDFS_SECONDARYNAMENODE_USER defined. Aborting operation.
```
* 配置环境变量
```
vim /etc/profile
export HDFS_NAMENODE_USER=root
export HDFS_DATANODE_USER=root
export HDFS_SECONDARYNAMENODE_USER=root
export YARN_RESOURCEMANAGER_USER=root
export YARN_NODEMANAGER_USER=root
```
* 再次sbin/start-dfs.sh
* 出现错误
```
bd01: ERROR: JAVA_HOME is not set and could not be found.
bd02: ERROR: JAVA_HOME is not set and could not be found.
bd03: ERROR: JAVA_HOME is not set and could not be found.
```
* 配置hadoop环境变量
```
vim etc/hadoop/hadoop-env.sh 
设置JAVA_HOME
```
* 再次sbin/start-dfs.sh
* 访问http://bd01:9870/
* 在bd03机器上hadoop目录下执行 sbin/start-yarn.sh
* 访问http://bd03:8088/
