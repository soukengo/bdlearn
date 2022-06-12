package cn.ibeihe.bigdata.chapters.ch04.work2

import cn.ibeihe.bigdata.context.SparkApp
import org.apache.commons.lang3.StringUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ListBuffer

/**
 * 作业二：使用 Spark 实现 Hadoop 分布式数据传输工具 DistCp (distributed copy)，只要求实现最基础的 copy 功能
 */
object SparkDistCp extends SparkApp("SparkDistCp") {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  var options = new Options()

  override def init(args: Array[String]): Unit = {
    super.init(args, options)
  }

  override def run(args: Array[String]): Unit = {
    if (StringUtils.isEmpty(options.src) || StringUtils.isEmpty(options.target)) {
      super.printUsage(System.err)
      return
    }
    // 获取源文件列表，并生成目标文件路径
    val files = checkFiles(options.src, options.target)
    // 考呗源文件到目标文件
    copyFiles(files, options.maxTask, options.ignoreErr)
  }


  def checkFiles(src: String, target: String): ListBuffer[FilePair] = {
    val configuration = sc.hadoopConfiguration
    val fileSystem = FileSystem.get(configuration)
    var files = ListBuffer[FilePair]()
    fileSystem.listStatus(new Path(src)).foreach(f => {
      val srcFile = f.getPath.toString
      val targetFile = f.getPath.toString.replaceAll(src, target)
      if (f.isDirectory) {
        files = files ++ checkFiles(srcFile, targetFile)
        // 提前创建目录
        fileSystem.mkdirs(new Path(targetFile))
      } else {
        files.append(FilePair(srcFile, targetFile))
      }
    })
    files
  }

  def copyFiles(files: ListBuffer[FilePair], taskNum: Int, ignoreErr: Boolean): Unit = {
    val tasks = sc.parallelize(files, taskNum)
    tasks.foreach(item => {
      // 这里不能通过sc.hadoopConfiguration获取 会报错
      val conf = new Configuration()
      val fileSystem = FileSystem.get(conf)
      try {
        FileUtil.copy(fileSystem, new Path(item.src), fileSystem, new Path(item.target), false, true, conf)
        logger.info("copy {} success", item.src)
      } catch {
        case e: Exception =>
          if (ignoreErr) {
            logger.error("copy file error: {}", e.getMessage)
          } else {
            throw e
          }
      }
    })

  }

}
