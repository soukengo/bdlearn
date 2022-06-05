package cn.ibeihe.bigdata.config

import scala.beans.BeanProperty

class HBaseConfig extends Serializable {
  val NAMESPACE_SPLITTER = ":"
  @BeanProperty
  var zookeeperQuorum: String = _
  @BeanProperty
  var namespace: String = _
}