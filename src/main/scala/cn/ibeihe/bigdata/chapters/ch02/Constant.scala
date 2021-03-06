package cn.ibeihe.bigdata.chapters.ch02

import cn.ibeihe.bigdata.config.Config

object Constant {
  val CH = "/ch02"

  def getWorkDir: String = {
    Config.getConfig.app.userDir + CH
  }

  def getLocalWorkDir: String = {
    Config.getConfig.app.localUserDir + CH
  }
}
