package cn.ibeihe.bigdata.chapters.ch02

import org.apache.hadoop.io.Writable

import java.io.{DataInput, DataOutput}

case class PhoneStat(var up: Long, var down: Long, var total: Long) extends Writable {
  def this() = {
    this(0, 0, 0)
  }

  override def write(dataOutput: DataOutput): Unit = {
    dataOutput.writeLong(up)
    dataOutput.writeLong(down)
    dataOutput.writeLong(total)
  }

  override def readFields(dataInput: DataInput): Unit = {
    this.up = dataInput.readLong()
    this.down = dataInput.readLong()
    this.total = dataInput.readLong()
  }

  override def toString: String = {
    return up + "\t" + down + "\t" + total
  }
}
