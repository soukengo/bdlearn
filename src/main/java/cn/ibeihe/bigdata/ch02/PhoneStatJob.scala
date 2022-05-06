package cn.ibeihe.bigdata.ch02

import org.apache.hadoop.conf.{Configuration, Configured}
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.util.{Tool, ToolRunner}
import org.slf4j.{Logger, LoggerFactory}


object PhoneStatJob extends Configured with Tool {

  var logger: Logger = LoggerFactory.getLogger(PhoneStatJob.getClass)

  def main(args: Array[String]): Unit = {
    val conf: Configuration = new Configuration();
    System.exit(ToolRunner.run(conf, this, args));
  }

  var inputFileName = "/HTTP_20130313143750.dat"


  override def run(args: Array[String]): Int = {
    val conf = getConf
    val fs = FileSystem.get(conf)
    val job = Job.getInstance(conf, "PhoneStatJob")
    job.setJarByClass(this.getClass)
    job.setMapperClass(classOf[PhoneStatMapper])
    job.setReducerClass(classOf[PhoneStatReducer])
    //设置map输出的key value
    job.setMapOutputKeyClass(classOf[Text]);
    job.setMapOutputValueClass(classOf[PhoneStat])

    //设置reduce 输出的 key value
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])
    job.setNumReduceTasks(1)
    val input: String = Constant.WORK_DIR + "/input"
    val inputPath = new Path(input)
    fs.deleteOnExit(inputPath)
    fs.mkdirs(inputPath)
    val localInputFilePath = new Path(Constant.LOCAL_WORK_DIR + inputFileName)
    fs.copyFromLocalFile(localInputFilePath, inputPath)
    val output: String = Constant.WORK_DIR + "/output"
    val outputPath = new Path(output)
    fs.delete(outputPath, true)
    //设置输入输出的路径
    FileInputFormat.setInputPaths(job, Array[Path](inputPath): _*)
    FileOutputFormat.setOutputPath(job, outputPath)

    //提交job
    val success = job.waitForCompletion(true)
    if (!success) {
      System.out.println("PhoneStatJob task fail!")
      return 0
    }
    return 1
  }


}
