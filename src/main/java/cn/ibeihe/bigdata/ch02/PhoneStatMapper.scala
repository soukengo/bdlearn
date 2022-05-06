package cn.ibeihe.bigdata.ch02

import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

class PhoneStatMapper extends Mapper[LongWritable, Text, Text, PhoneStat] {

  override def map(key: LongWritable, row: Text, context: Mapper[LongWritable, Text, Text, PhoneStat]#Context): Unit = {
    val line = row.toString
    if (line.isEmpty) {
      return
    }

    val tokens: Array[String] = line.split("\t")
    if (tokens.length != 11) {
      return
    }

    val phone: Text = new Text(tokens(1))
    val upStr = tokens(8)
    val downStr = tokens(9)

    val up = Integer.parseInt(upStr)
    val down = Integer.parseInt(downStr)
    context.write(phone, PhoneStat(up = up, down = down, total = up + down))
  }

}
