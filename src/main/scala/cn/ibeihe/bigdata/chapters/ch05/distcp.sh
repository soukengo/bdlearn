#!/bin/bash

spark-submit --deploy-mode client  --master yarn --class cn.ibeihe.bigdata.chapters.ch05.work2.SparkDistCp bdlearn-1.0.jar -i -m 8 -s /user/student5/wusq/ch05/work2/data -t /user/student5/wusq/ch05/work2/data_cp