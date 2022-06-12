package cn.ibeihe.bigdata.context

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
 * Spark 应用基础类
 * 封装了初始化参数配置逻辑
 *
 * @param appName 应用名称
 */
abstract class SparkApp(val appName: String) extends Configured {
  protected lazy val spark: SparkSession = {
    val spark = SparkSession
      .builder
      .appName(appName)
      .config("hbase.zookeeper.quorum", getConfig.hbase.zookeeperQuorum)
      .config("spark.serializer", getConfig.spark.serializer)
      .getOrCreate()
    if (getConfig.hadoop != null && getConfig.hadoop.hdfsUrl != null) {
      spark.sparkContext.hadoopConfiguration.set("fs.defaultFS", getConfig.hadoop.hdfsUrl)
    }
    spark
  }
  protected lazy val sc: SparkContext = spark.sparkContext

  def main(args: Array[String]): Unit = {
    this.init(args)
    this.run(args)
  }

  def run(args: Array[String]): Unit
}
