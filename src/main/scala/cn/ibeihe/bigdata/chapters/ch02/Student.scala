package cn.ibeihe.bigdata.chapters.ch02

object Student {
  val TABLE_NAME: String = "student"
  val COL_FAMILY_NAME = "name"
  val COL_FAMILY_INFO = "info"
  val COL_FAMILY_SCORE = "score"

  val COL_NAME = "name"
  val COL_STUDENT_ID = "student_id"
  val COL_CLASS = "class"
  val COL_UNDERSTANDING = "understanding"
  val COL_PROGRAMMING = "programming"

  val DESCRIPTOR: Map[String, Array[String]] = Map(
    COL_FAMILY_NAME -> Array[String](),
    COL_FAMILY_INFO -> Array[String](COL_STUDENT_ID, COL_CLASS),
    COL_FAMILY_SCORE -> Array[String](COL_UNDERSTANDING, COL_PROGRAMMING)
  )
}

case class Student(var rowKey: String, var name: String, var studentId: String, var clazz: String, var understanding: Double, var programming: Double) {
  def this() {
    this("", "", "", "", 0, 0)
  }

  override def toString: String = s"Student(rowKey=$rowKey, name=$name, studentId=$studentId, class=$clazz, understanding=$understanding, programming=$programming)"
}