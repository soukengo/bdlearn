package cn.ibeihe.bigdata.chapters.ch07.work3

import org.apache.spark.sql.SparkSessionExtensions

class MySparkSessionExtension extends (SparkSessionExtensions => Unit) {

  override def apply(extensions: SparkSessionExtensions): Unit = {
    extensions.injectOptimizerRule { session =>
      MyPushDown(session)
    }
  }
}
