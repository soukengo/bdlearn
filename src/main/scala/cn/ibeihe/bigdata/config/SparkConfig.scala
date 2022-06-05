package cn.ibeihe.bigdata.config

import scala.beans.BeanProperty


class SparkConfig extends Serializable {
  @BeanProperty
  var master: String = _
  @BeanProperty
  var serializer: String = _
}
