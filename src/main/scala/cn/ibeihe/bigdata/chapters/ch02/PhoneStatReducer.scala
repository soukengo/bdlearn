package cn.ibeihe.bigdata.chapters.ch02

import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer

import java.lang

class PhoneStatReducer extends Reducer[Text, PhoneStat, Text, PhoneStat] {


  override def reduce(key: Text, values: lang.Iterable[PhoneStat], context: Reducer[Text, PhoneStat, Text, PhoneStat]#Context): Unit = {
    val result: PhoneStat = PhoneStat(0, 0, 0)
    values.forEach((item) => {
      result.up = result.up + item.up
      result.down = result.down + item.down
      result.total = result.total + item.total
    })
    context.write(key, result)
  }
}
