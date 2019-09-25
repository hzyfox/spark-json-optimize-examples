package com.fox.examples.sql

import java.util.Date

import com.fox.examples.util.CreateTableCLI
import org.apache.commons.cli.HelpFormatter
import org.apache.spark.sql.SparkSession

import scala.util.matching.Regex

/**
  * @author zyp
  */
object CacheNobench {
  def main(args: Array[String]): Unit = {
    val helper = "CreateTable:creating table for experiments"+
      " \n -h or --help to view helper"

    val (commandLine, options) = CreateTableCLI.parseCommandLine(args)
    val formatter = new HelpFormatter()

    if (commandLine.hasOption("h")) {
      CreateTableCLI.printHelpAndExit(formatter, options, helper, -1)
    }

    val spark = SparkSession
      .builder()
      .config("spark.sql.catalogImplementation", "hive")
      .config("spark.sql.json.writeCache",true)
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._

    val filePath = commandLine.getOptionValue("s")
    //    val userDBName = commandLine.getOptionValue("db")
    //    val pattern = new Regex("""jsonpath_(\w+)?_""")
    //    val tableName = pattern.findFirstMatchIn(filePath).get.group(1)
    //    println(s"tableName:$tableName")
    assert(filePath != null)
    val tableMap = spark.sparkContext.textFile(filePath).map(line => {
      val jsonPathInfoArr = line.split(",")
      val dbName = jsonPathInfoArr(0)
      val tableName = jsonPathInfoArr(1)
      val columnName = jsonPathInfoArr(2)
      val jsonPath = jsonPathInfoArr(3)
      (dbName+"."+tableName,columnName+"#"+jsonPath)
    })
      .groupByKey()
      .map(v => {
        val iter = v._2.toIterator
        val path = iter.next()
        val pathArr = path.split("#")
        val alias = s"${pathArr(0)}_${pathArr(1)}".replace(".","_").replace("[","").replace("]","")
        var wrappedJsonPath = s"get_json_object(${pathArr(0)}, '$$.${pathArr(1)}') as ${alias}"
        while(iter.hasNext){
          val path = iter.next()
          val pathArr = path.split("#")
          val alias = s"${pathArr(0)}_${pathArr(1)}".replace(".","_").replace("[","").replace("]","")
          wrappedJsonPath += s",get_json_object(${pathArr(0)}, '$$.${pathArr(1)}') as ${alias}"
        }
        (v._1,wrappedJsonPath)
      }).collect().toMap

    var st = new Date().getTime

    var i = 0;
    for(x <- tableMap){
      val dbAndTableName = x._1.split("\\.")
      val dbName = dbAndTableName(0)
      val tableName = dbAndTableName(1)
      val selectContentString = x._2
      println(selectContentString)
      spark.sql(s"drop table if exists ${dbName}_${tableName}")

      val sst =  new Date().getTime
      val sql = s"select ${selectContentString},uuid1,uuid2,uuid3 from default.newnobench"
      println(sql)
      val df = spark.sql(sql)
      println(df.count())
        df.write.format("hive").option("fileFormat","orc").mode("overwrite").saveAsTable("default.default_newnobench")
      val eet = new Date().getTime
      println(s"$tableName $i cache: First execution time = ${(eet-sst)/1000.0}s ")
      i +=1;
    }

    var et = new Date().getTime
    println(s"TestSQL 1: First execution time = ${(et-st)/1000.0}s ")




  }
}
