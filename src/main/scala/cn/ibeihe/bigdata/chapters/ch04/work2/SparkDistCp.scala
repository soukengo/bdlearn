package cn.ibeihe.bigdata.chapters.ch04.work2

import cn.ibeihe.bigdata.context
import cn.ibeihe.bigdata.context.SparkApp
import org.kohsuke.args4j.CmdLineParser

import scala.collection.JavaConverters.seqAsJavaListConverter

/**
 * 作业二：使用 Spark 实现 Hadoop 分布式数据传输工具 DistCp (distributed copy)，只要求实现最基础的 copy 功能
 */
object SparkDistCp extends SparkApp("SparkDistCp") {

  var defaultTaskNum = 1;

  var options = new Options()

  override def init(args: Array[String]): Unit = {
    super.init(args, options)
  }

  override def run(args: Array[String]): Unit = {

  }

}
