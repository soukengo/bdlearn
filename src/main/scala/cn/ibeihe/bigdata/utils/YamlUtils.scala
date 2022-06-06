package cn.ibeihe.bigdata.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

import java.io.InputStream

object YamlUtils {
  private val mapper = new ObjectMapper(new YAMLFactory())
  //  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def readConfig[T](stream: InputStream, clazz: Class[T]): T = {
    mapper.readValue(stream, clazz)
  }
}
