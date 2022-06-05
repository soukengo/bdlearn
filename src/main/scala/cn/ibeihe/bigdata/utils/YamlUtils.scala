package cn.ibeihe.bigdata.utils

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

import java.io.File

object YamlUtils {
  private val mapper = new ObjectMapper(new YAMLFactory())
//  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def readConfig[T](path: String, clazz: Class[T]): T = {
    mapper.readValue(new File(path), clazz)
  }
}
