package cn.ibeihe.bigdata.chapters.ch05.work1

import cn.ibeihe.bigdata.chapters.ch05.constant.Constant
import cn.ibeihe.bigdata.chapters.ch05.work2.SparkDistCp.sc
import cn.ibeihe.bigdata.context.SparkApp
import org.apache.hadoop.fs.{FileSystem, Path}

/**
 * 作业一：使用 RDD API 实现带词频的倒排索引
 */
object WordInvertedIndexV2 extends SparkApp("WordInvertedIndexV2") {

  override def init(args: Array[String]): Unit = {
    super.init(args)
  }

  override def run(args: Array[String]): Unit = {
    val configuration = sc.hadoopConfiguration
    val fileSystem = FileSystem.get(configuration)
    val path = new Path(s"${Constant.getWorkDir}/work1/data")
    val files = fileSystem.listFiles(path, true)
    sc.parallelize()
  }

}
