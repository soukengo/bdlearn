package cn.ibeihe.bigdata.context

import cn.ibeihe.bigdata.config.Config
import org.kohsuke.args4j.CmdLineParser

import scala.jdk.CollectionConverters.SeqHasAsJava

class Configured {
  val options = new Options()
  val parser = new CmdLineParser(options)

  def init(args: Array[String]): Unit = {

    parser.parseArgument(args.toList.asJava)
    if (options.config == null || options.config.isEmpty) {
      return
    }
    Config.load(options.config)
  }

  def getConfig: Config = {
    val config = Config.getConfig
    if (config == null) {
      parser.printUsage(System.err)
      throw new IllegalArgumentException("未指定配置文件")
    }
    config
  }
}
