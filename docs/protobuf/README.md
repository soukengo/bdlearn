## Macbook M1 PRO 虚拟机（centos/ubuntu）编译protobuf 2.5.0

### 1.安装

```shell
wget https://github.com/protocolbuffers/protobuf/releases/download/v2.5.0/protobuf-2.5.0.tar.gz
tar -zxvf  protobuf-2.5.0.tar.gz
./configure --prefix=/usr/local/protobuf
make 
make install
```

### 2.常见错误

#### 2.1 Atomic operations are not supported on your platform

* 报错信息

```vim 
/bin/sh ../libtool  --tag=CXX   --mode=compile g++ -DHAVE_CONFIG_H -I. -I..    -pthread -Wall -Wwrite-strings -Woverloaded-virtual -Wno-sign-compare -O2 -g -DNDEBUG -MT atomicops_internals_x86_gcc.lo -MD -MP -MF .deps/atomicops_internals_x86_gcc.Tpo -c -o atomicops_internals_x86_gcc.lo `test -f 'google/protobuf/stubs/atomicops_internals_x86_gcc.cc' || echo './'`google/protobuf/stubs/atomicops_internals_x86_gcc.cc
libtool: compile:  g++ -DHAVE_CONFIG_H -I. -I.. -pthread -Wall -Wwrite-strings -Woverloaded-virtual -Wno-sign-compare -O2 -g -DNDEBUG -MT atomicops_internals_x86_gcc.lo -MD -MP -MF .deps/atomicops_internals_x86_gcc.Tpo -c google/protobuf/stubs/atomicops_internals_x86_gcc.cc  -fPIC -DPIC -o .libs/atomicops_internals_x86_gcc.o
In file included from google/protobuf/stubs/atomicops_internals_x86_gcc.cc:36:
./google/protobuf/stubs/atomicops.h:165:1: error: stray ‘#’ in program
  165 | #error "Atomic operations are not supported on your platform"
      | ^
./google/protobuf/stubs/atomicops.h:192:1: note: in expansion of macro ‘GOOGLE_PROTOBUF_ATOMICOPS_ERROR’
  192 | GOOGLE_PROTOBUF_ATOMICOPS_ERROR
      | ^~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
./google/protobuf/stubs/atomicops.h:165:2: error: ‘error’ does not name a type; did you mean ‘perror’?
  165 | #error "Atomic operations are not supported on your platform"
      |  ^~~~~
./google/protobuf/stubs/atomicops.h:192:1: note: in expansion of macro ‘GOOGLE_PROTOBUF_ATOMICOPS_ERROR’
  192 | GOOGLE_PROTOBUF_ATOMICOPS_ERROR
      | ^~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
make[2]: *** [Makefile:1481: atomicops_internals_x86_gcc.lo] Error 1
make[2]: Leaving directory '/opt/src/protobuf-2.5.0/src'
make[1]: *** [Makefile:669: all-recursive] Error 1
make[1]: Leaving directory '/opt/src/protobuf-2.5.0'
make: *** [Makefile:576: all] Error 2
```

* 报错原因
    * m1是arm64架构 通过 arch命令可以看到值为：aarch64

* 解决方案
    * 1.修改 src/google/protobuf/stubs/platform_macros.h
      * 找到以下内容

        ```C
        #else
        #error Host architecture was not detected as supported by protobuf 
        ```
      * 在它上面加入以下内容

        ```c
        #elif defined(__aarch64__) || defined(_M_ARM64)
        #define GOOGLE_PROTOBUF_ARCH_AARCH64 1
        #define GOOGLE_PROTOBUF_ARCH_64_BIT 1
        ```
    * 2.将[atomicops_internals_arm64_gcc.h](atomicops_internals_arm64_gcc.h)放入src/google/protobuf/stubs目录下
    * 3.修改 src/google/protobuf/stubs/atomicops.h 文件
      * 找到以下内容
      
      ```C
      #elif defined(GOOGLE_PROTOBUF_ARCH_ARM)
      #include <google/protobuf/stubs/atomicops_internals_arm_gcc.h> 
      ```
      * 在它下面加入以下内容
      
      ```C
        #elif defined(GOOGLE_PROTOBUF_ARCH_AARCH64)
        #include <google/protobuf/stubs/atomicops_internals_arm64_gcc.h>
      ```
    * 4.重新执行make && make install 
