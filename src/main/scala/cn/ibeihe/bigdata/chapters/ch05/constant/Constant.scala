package cn.ibeihe.bigdata.chapters.ch05.constant

import cn.ibeihe.bigdata.config.Config

object Constant {
  val CH = "/ch05"

  def getWorkDir: String = {
    Config.getConfig.app.userDir + CH
  }

  def getLocalWorkDir: String = {
    Config.getConfig.app.localUserDir + CH
  }
}
