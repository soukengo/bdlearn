package cn.ibeihe.bigdata.config

import cn.ibeihe.bigdata.utils.YamlUtils

import java.net.URL
import scala.beans.BeanProperty


object Config {
  private val defaultConfigFileURL: URL = getClass.getResource("/app.yaml")
  private var configFilePath: String = _
  private var config: Config = _
  println(defaultConfigFileURL)
  if (defaultConfigFileURL != null) {
    configFilePath = defaultConfigFileURL.getPath
    config = load(configFilePath)
  }

  def load(path: String): Config = {
    configFilePath = path
    config = YamlUtils.readConfig(configFilePath, classOf[Config])
    config
  }

  def getConfig: Config = {
    config
  }
}


class Config extends Serializable {
  @BeanProperty
  var app: AppConfig = new AppConfig()
  @BeanProperty
  var hadoop: HadoopConfig = new HadoopConfig()
  @BeanProperty
  var hbase: HBaseConfig = new HBaseConfig()
  @BeanProperty
  var spark: SparkConfig = new SparkConfig()
}





