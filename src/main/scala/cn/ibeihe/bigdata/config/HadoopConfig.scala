package cn.ibeihe.bigdata.config

import scala.beans.BeanProperty

class HadoopConfig extends Serializable {
  @BeanProperty
  var hdfsUrl: String = _
}