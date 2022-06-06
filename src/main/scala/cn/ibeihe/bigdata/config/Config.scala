package cn.ibeihe.bigdata.config

import cn.ibeihe.bigdata.utils.YamlUtils

import java.io.{FileInputStream, InputStream}
import scala.beans.BeanProperty


object Config {
  private val defaultConfigFile: InputStream = getClass.getResourceAsStream("/app.yaml")
  private var config: Config = _
  if (defaultConfigFile != null) {
    config = load(defaultConfigFile)
  }

  def load(path: String): Config = {
    val stream = new FileInputStream(path)
    config = this.load(stream)
    stream.close()
    config
  }

  def load(stream: InputStream): Config = {
    config = YamlUtils.readConfig(stream, classOf[Config])
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





