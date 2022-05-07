## Hbase常见错误解决

### 1. Server is not running yet

在hbase-env.sh文件中配置环境变量

```shell
export HBASE_DISABLE_HADOOP_CLASSPATH_LOOKUP=true
```

### 2.Exception in thread "main" java.lang.NoSuchMethodError: org.apache.hadoop.security.HadoopKerberosName.setRuleMechanism(Ljava/lang/String;)V

导入hadoop-auth

```xml

<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-auth</artifactId>
    <version>${hadoop.version}</version>
</dependency>
```

### 3. 程序卡住很长时间，并输出错误：failed for get of /hbase/hbaseid, code = CONNECTIONLOSS, retries = 1

设置java运行参数

```java
System.setProperty("zookeeper.sasl.client","false");
```


