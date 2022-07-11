## Macbook M1 PRO 虚拟机（centos）安装presto
### 1.准备工作
* 复用安装好的hadoop集群环境 => [hadoop集群安装](../hadoop/install.md)
* 规划节点: bd01,bd02,bd03
* 其中bd01为master节点

### 2.编译presto
* 参考：https://bbs.huaweicloud.com/forum/thread-54993-1-1.html

### 3.常见错误
#### 3.1 yum install 找不到snappy-devel时可以在这里下载然后手动安装
* https://rpmfind.net/
#### 3.2 安装protobuf2.5
* [Macbook M1 PRO 虚拟机（centos/ubuntu）编译protobuf 2.5.0](../protobuf/README.md)