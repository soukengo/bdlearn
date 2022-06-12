package cn.ibeihe.bigdata.chapters.ch04.constant

import cn.ibeihe.bigdata.config.Config

object Constant {
  val CH = "/ch04"

  def getWorkDir: String = {
    Config.getConfig.app.userDir + CH
  }

  def getLocalWorkDir: String = {
    Config.getConfig.app.localUserDir + CH
  }
}
