package cn.ibeihe.bigdata.config

import scala.beans.BeanProperty

class AppConfig extends Serializable {
  @BeanProperty
  var userDir: String = _
  @BeanProperty
  var localUserDir: String = _
  @BeanProperty
  var user: String = _
}