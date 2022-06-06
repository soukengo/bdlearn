package cn.ibeihe.bigdata.utils

import org.apache.commons.io.FilenameUtils

object FileUtils {

  def baseName(file: String): String = {
    return FilenameUtils.getBaseName(file)
  }

}
