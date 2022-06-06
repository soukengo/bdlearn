package cn.ibeihe.bigdata.config

import scala.beans.BeanProperty


class SparkConfig extends Serializable {
  @BeanProperty
  var serializer: String = _
}
