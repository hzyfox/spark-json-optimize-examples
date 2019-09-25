package com.fox.examples.sql

import java.util.UUID

import org.apache.spark.sql.{Row, SparkSession}

/**
  * @author zyp
  */

object CreateNewNobench {
  case class NewNobench(json:String,uuid1:String,uuid2:String,uuid3:String)
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .config("spark.sql.catalogImplementation", "hive")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._

    val df = spark.sql("select * from nobench limit 20000000").map(row=>{
      val json = row.get(0).toString
      val uuid1 = UUID.randomUUID().toString
      val uuid2 = UUID.randomUUID().toString
      val uuid3 = UUID.randomUUID().toString
      NewNobench(json,uuid1,uuid2,uuid3)
    }).toDF("json","uuid1","uuid2","uuid3")
      .write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable("newnobench")
  }

}
