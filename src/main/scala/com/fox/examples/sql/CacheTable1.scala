package com.fox.examples.sql


import com.fox.examples.util.CreateTableCLI
import org.apache.commons.cli.HelpFormatter
import org.apache.spark.sql.SparkSession

import scala.io.Source

/**
  * @author zyp
  */
object CacheTable1 {
  def main(args: Array[String]): Unit = {
    val helper = "CreateTable:creating table for experiments" +
      " \n -h or --help to view helper"

    val (commandLine, options) = CreateTableCLI.parseCommandLine(args)
    val formatter = new HelpFormatter()

    if (commandLine.hasOption("h")) {
      CreateTableCLI.printHelpAndExit(formatter, options, helper, -1)
    }

    val spark = SparkSession
      .builder()
      .config("spark.sql.catalogImplementation", "hive")
      .config("spark.sql.json.writeCache", true)
      .enableHiveSupport()
      .getOrCreate()

    val filePath = commandLine.getOptionValue("s")
    //    val pattern = new Regex("""jsonpath_(\w+)?_""")
    //    val tableName = pattern.findFirstMatchIn(filePath).get.group(1)
    //    println(s"tableName:$tableName")
    assert(filePath != null)
    val splitter = "####"
    Source.fromInputStream(this.getClass.getClassLoader
      .getResourceAsStream(filePath)).getLines().filter(_.nonEmpty).map(line => {
      println(s"=================================$line=======================================")
      val jsonPathInfoArr = line.split(",")
      val dbName = jsonPathInfoArr(0)
      val tableName = jsonPathInfoArr(1)
      val columnName = jsonPathInfoArr(2)
      val jsonPath = jsonPathInfoArr(3)
      (dbName + splitter + tableName, columnName + splitter + jsonPath)
    }).toList.groupBy(_._1).map(item => (item._1, item._2.map(_._2))).map(kvs => {
      val iter = kvs._2.iterator
      val fullPath = iter.next()
      val pathArrs = fullPath.split(splitter)
      val alias = s"${pathArrs(0)}_${pathArrs(1)}".replace(".", "_").replaceAll("\\[|\\]", "")
      val sql = new StringBuilder
      sql.append(s"get_json_object(${pathArrs(0)}, '$$.${pathArrs(1)}') as $alias")
      iter.foreach(path => {
        val pathArrs = fullPath.split(splitter)
        val alias = s"${pathArrs(0)}_${pathArrs(1)}".replace(".", "_").replaceAll("\\[|\\]", "")
        sql.append(s",get_json_object(${pathArrs(0)}, '$$.${pathArrs(1)}') as $alias")
      })
      (kvs._1, sql.toString)
    }).foreach(kvs => {
      val dbAndTableName = kvs._1.split(splitter)
      val dbName = dbAndTableName(0)
      val tableName = dbAndTableName(1)
      val sql = kvs._2
      println(sql)
      spark.sql(s"select $sql from $dbName.$tableName").write.format("hive").option("fileFormat", "orc").mode("append").saveAsTable(s"${dbName}_$tableName")
      spark.sql(s"select * from ${dbName}_$tableName limit 1").show()
    })

  }
}

