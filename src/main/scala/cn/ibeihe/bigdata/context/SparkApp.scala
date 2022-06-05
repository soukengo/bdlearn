package cn.ibeihe.bigdata.context

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

abstract class SparkApp(val appName: String) extends Configured {
  protected lazy val spark: SparkSession = {
    val spark = SparkSession
      .builder
      .appName(appName)
      .master(getConfig.spark.master)
      .config("hbase.zookeeper.quorum", getConfig.hbase.zookeeperQuorum)
      .config("spark.serializer", getConfig.spark.serializer)
      .getOrCreate()
    spark.sparkContext.hadoopConfiguration.set("fs.defaultFS", getConfig.hadoop.hdfsUrl)
    spark
  }
  protected lazy val sc: SparkContext = spark.sparkContext

  override def init(args: Array[String]): Unit = {
    super.init(args)
  }

  def main(args: Array[String]): Unit = {
    this.init(args)
    this.run(args)
  }

  def run(args: Array[String]): Unit
}
