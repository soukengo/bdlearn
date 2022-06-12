package cn.ibeihe.bigdata.context

import org.kohsuke.args4j.Option

class Options {

  @Option(name = "--help", aliases = Array("-h"), usage = "show usage", required = false)
  var help: Boolean = _

  @Option(name = "--config", aliases = Array("-c"), usage = "config file path", required = false)
  var config: String = _
}
