package cn.ibeihe.bigdata.context

import org.kohsuke.args4j.Option

class Options {

  @Option(name = "--config", aliases = Array("-c"), usage = "config file path", required = false)
  var config: String = _
}
