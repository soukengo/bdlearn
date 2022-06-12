package cn.ibeihe.bigdata.context

import cn.ibeihe.bigdata.config.Config
import org.kohsuke.args4j.CmdLineParser

import java.io.OutputStream
import scala.collection.JavaConverters.seqAsJavaListConverter

/**
 * 配置解析
 */
class Configured {

  private var parser: CmdLineParser = _

  def init(args: Array[String]): Unit = {
    this.init(args, new Options())
  }

  def init(args: Array[String], options: Options): Unit = {
    parser = new CmdLineParser(options)
    parser.parseArgument(args.toList.asJava)
    if (options.config == null || options.config.isEmpty) {
      return
    }
    Config.load(options.config)
  }

  def getConfig: Config = {
    val config = Config.getConfig
    if (config == null) {
      printUsage(System.err)
      throw new IllegalArgumentException("未指定配置文件")
    }
    config
  }

  def printUsage(out: OutputStream): Unit = {
    parser.printUsage(out)
  }
}

