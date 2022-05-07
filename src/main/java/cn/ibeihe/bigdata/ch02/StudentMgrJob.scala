package cn.ibeihe.bigdata.ch02

import cn.ibeihe.bigdata.config.Config
import org.apache.hadoop.conf.{Configuration, Configured}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HConstants, NamespaceDescriptor, TableName}
import org.apache.hadoop.util.{Tool, ToolRunner}
import org.slf4j.{Logger, LoggerFactory}

import java.util

object StudentMgrJob extends Configured with Tool {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  var conn: Connection = _
  var admin: Admin = _

  def main(args: Array[String]): Unit = {
    System.setProperty("jdk.tls.rejectClientInitiatedRenegotiation", "true")
    System.setProperty("zookeeper.sasl.client", "false")
    val conf: Configuration = new Configuration();
    //    conf.addResource("hadoop-default.xml")
    if (args.length > 0) {
      conf.set("hbase.zookeeper.quorum", args(0))
    }
    System.exit(ToolRunner.run(conf, this, args));
  }

  override def run(strings: Array[String]): Int = {
    logger.info("start running...")
    val conf = getConf
    this.conn = ConnectionFactory.createConnection(conf)
    this.admin = conn.getAdmin
    this.createNamespace()
    this.createTable()

    var students = this.getStudents()
    logger.info("before insert, students: {}", students)
    this.insertStudents()
    students = this.getStudents()
    logger.info("after insert, students: {}", students)

    val studentId = "G20220698080053"
    this.deleteStudent(studentId)
    students = this.getStudents()
    logger.info("after delete student {}, students: {}", studentId, students)
    this.deleteStudents()
    students = this.getStudents()
    logger.info("after delete all, students: {}", students)

    logger.info("end running")
    return 1
  }

  def createNamespace(): Unit = {
    val list = admin.listNamespaces()
    if (list.contains(Config.HBASE_NAMESPACE)) {
      return
    }
    admin.createNamespace(NamespaceDescriptor.create(Config.HBASE_NAMESPACE).build())
  }

  def createTable(): Unit = {
    val tableName = TableName.valueOf(Student.TABLE_NAME)
    if (admin.tableExists(tableName)) {
      return
    }
    ColumnFamilyDescriptorBuilder.newBuilder(HConstants.CATALOG_FAMILY)
    val builder = TableDescriptorBuilder.newBuilder(tableName)
    Student.DESCRIPTOR.foreach(item => {
      builder.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(item._1)).build())
    })
    admin.createTable(builder.build())
  }

  // 插入数据
  def insertStudents(): Unit = {
    this.deleteStudents()
    // 准备数据
    val students = Array[Student](
      Student("2021000000001", "Tom", "2021000000001", "1", 75, 82),
      Student("2021000000002", "Tom", "2021000000002", "1", 85, 67),
      Student("2021000000003", "Tom", "2021000000003", "1", 80, 80),
      Student("2021000000004", "Tom", "2021000000004", "1", 60, 61),
      Student("G20220698080053", "wusq", "G20220698080053", "1", 100, 100),
    )

    students.foreach(item => {
      insertStudent(item)
    })
  }

  // 插入单个Student
  def insertStudent(student: Student): Unit = {
    val tableName = TableName.valueOf(Student.TABLE_NAME)
    val table = conn.getTable(tableName)
    val row = new Put(Bytes.toBytes(student.rowKey))
    row.addColumn(Bytes.toBytes(Student.COL_FAMILY_NAME), Bytes.toBytes(Student.COL_NAME), Bytes.toBytes(student.name))

    row.addColumn(Bytes.toBytes(Student.COL_FAMILY_INFO), Bytes.toBytes(Student.COL_STUDENT_ID), Bytes.toBytes(student.studentId))
    row.addColumn(Bytes.toBytes(Student.COL_FAMILY_INFO), Bytes.toBytes(Student.COL_CLASS), Bytes.toBytes(student.clazz))

    row.addColumn(Bytes.toBytes(Student.COL_FAMILY_SCORE), Bytes.toBytes(Student.COL_UNDERSTANDING), Bytes.toBytes(student.understanding))
    row.addColumn(Bytes.toBytes(Student.COL_FAMILY_SCORE), Bytes.toBytes(Student.COL_PROGRAMMING), Bytes.toBytes(student.programming))
    table.put(row)
  }

  def deleteRow(table: Table, rowKey: String): Unit = {
    val command = new Delete(Bytes.toBytes(rowKey))
    table.delete(command)
  }

  def deleteStudent(studentId: String): Unit = {
    val tableName = TableName.valueOf(Student.TABLE_NAME)
    val table = conn.getTable(tableName)
    deleteRow(table, studentId)
  }

  def deleteStudents(): Unit = {
    val tableName = TableName.valueOf(Student.TABLE_NAME)
    // 先清空数据
    admin.disableTable(tableName)
    admin.truncateTable(tableName, false)
    //    admin.enableTable(tableName)
  }

  def getStudents(): Array[Student] = {
    val tableName = TableName.valueOf(Student.TABLE_NAME)
    val table = conn.getTable(tableName)
    val scan = new Scan()
    val scanner = table.getScanner(scan)
    val list = new util.ArrayList[Student]()
    scanner.forEach(result => {
      val item = new Student()
      item.rowKey = Bytes.toString(result.getRow)
      item.name = Bytes.toString(result.getValue(Bytes.toBytes(Student.COL_FAMILY_NAME), Bytes.toBytes(Student.COL_NAME)))
      item.studentId = Bytes.toString(result.getValue(Bytes.toBytes(Student.COL_FAMILY_INFO), Bytes.toBytes(Student.COL_STUDENT_ID)))
      item.clazz = Bytes.toString(result.getValue(Bytes.toBytes(Student.COL_FAMILY_INFO), Bytes.toBytes(Student.COL_CLASS)))
      item.understanding = Bytes.toDouble(result.getValue(Bytes.toBytes(Student.COL_FAMILY_SCORE), Bytes.toBytes(Student.COL_UNDERSTANDING)))
      item.programming = Bytes.toDouble(result.getValue(Bytes.toBytes(Student.COL_FAMILY_SCORE), Bytes.toBytes(Student.COL_PROGRAMMING)))
      list.add(item)
    })
    scanner.close()
    return list.toArray(Array[Student]())
  }

}
