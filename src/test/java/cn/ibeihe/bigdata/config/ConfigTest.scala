package cn.ibeihe.bigdata.config


object ConfigTest {

  def main(args: Array[String]): Unit = {
    println(Config.getConfig.hbase.namespace)
  }

}
