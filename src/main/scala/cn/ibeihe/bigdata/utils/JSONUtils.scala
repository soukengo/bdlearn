package cn.ibeihe.bigdata.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.slf4j.{Logger, LoggerFactory}

object JSONUtils {
  val logger: Logger = LoggerFactory.getLogger(getClass)
  private val mapper = new ObjectMapper()
  mapper.registerModules(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


  def toJSONString(value: Any): String = {
    try return mapper.writeValueAsString(value)
    catch {
      case e: JsonProcessingException =>
        logger.error(e.getMessage, e)
    }
    null
  }
}
