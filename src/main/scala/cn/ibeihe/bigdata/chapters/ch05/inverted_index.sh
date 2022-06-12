#!/bin/bash

spark-submit --deploy-mode client  --master yarn --class cn.ibeihe.bigdata.chapters.ch05.work1.WordInvertedIndex bdlearn-1.0.jar