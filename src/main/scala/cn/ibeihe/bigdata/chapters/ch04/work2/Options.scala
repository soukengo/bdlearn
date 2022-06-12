package cn.ibeihe.bigdata.chapters.ch04.work2

import cn.ibeihe.bigdata.utils.JSONUtils
import org.kohsuke.args4j.Option

class Options extends cn.ibeihe.bigdata.context.Options {

  @Option(name = "--ignore", aliases = Array("-i"), usage = "ignore failures", required = false)
  var ignore: Boolean = false
  @Option(name = "--max", aliases = Array("-m"), usage = "max concurrence", required = false)
  var maxTask: Int = 0

  override def toString: String = {
    JSONUtils.toJSONString(this)
  }
}
