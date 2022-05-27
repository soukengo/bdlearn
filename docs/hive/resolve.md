## Hive常见错误解决

### 1. Caused by: java.lang.RuntimeException: java.lang.ClassNotFoundException: org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe
* 在hive-site.xml中增加以下配置

```xml
<property>
    <name>hive.aux.jars.path</name>
    <value>file:///usr/local/sbin/apache-hive-3.1.3-bin/lib/hive-contrib-3.1.3.jar</value>
</property> 
```

