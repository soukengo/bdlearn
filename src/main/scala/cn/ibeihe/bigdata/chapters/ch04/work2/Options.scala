package cn.ibeihe.bigdata.chapters.ch04.work2

import cn.ibeihe.bigdata.utils.JSONUtils
import org.kohsuke.args4j.Option

class Options extends cn.ibeihe.bigdata.context.Options {

  @Option(name = "--ignore", aliases = Array("-i"), usage = "ignore failures", required = false)
  var ignoreErr: Boolean = false
  @Option(name = "--max", aliases = Array("-m"), usage = "max concurrence", required = false)
  var maxTask: Int = 0

  @Option(name = "--src", aliases = Array("-s"), usage = "source file/dir", required = false)
  var src: String = _

  @Option(name = "--target", aliases = Array("-t"), usage = "target file/dir", required = false)
  var target: String = _


  override def toString: String = {
    JSONUtils.toJSONString(this)
  }
}
