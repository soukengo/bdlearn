package cn.ibeihe.bigdata.chapters.ch04

import cn.ibeihe.bigdata.context.SparkApp
import cn.ibeihe.bigdata.utils.FileUtils

/**
 * 作业一：使用 RDD API 实现带词频的倒排索引
 */
object WordInvertedIndex extends SparkApp("WordInvertedIndex") {

  override def init(args: Array[String]): Unit = {
    super.init(args)

  }

  override def run(args: Array[String]): Unit = {
    import spark.implicits._
    // step 1 加载目录下所有文件
    val files = sc.wholeTextFiles(s"${Constant.getWorkDir}/data")
    val res = files
      // step 2 拆分单词，并关联单词对应的文件名
      .flatMap(file =>
        file._2.replaceAll("\n", "").split(" ")
          .map(word => (word, Array(FileUtils.baseName(file._1))))
      )
      // step 3 根据key汇总计算得出单词所关联的文件列表
      .reduceByKey(_ ++ _)
      // step 4 分组计算每个单词在对应的文件出现的次数
      .map(item => (item._1, item._2.map((_, 1)).groupBy(_._1).map(v => (v._1, v._2.length)).toArray))
      .toDF("word", "count")
    res.show(false)
  }

}
