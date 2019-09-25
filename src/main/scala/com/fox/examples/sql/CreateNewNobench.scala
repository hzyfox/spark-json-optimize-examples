package com.fox.examples.sql

import java.util.UUID

import org.apache.spark.sql.{Row, SparkSession}

/**
  * @author zyp
  */

object CreateNewNobench {
  def main(args: Array[String]): Unit = {
    //  case class NewNobench(json:String,uuid1:String,uuid2:String,uuid3:String)
    val spark = SparkSession
      .builder()
      .config("spark.sql.catalogImplementation", "hive")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._

    val df = spark.sql("select * from nobench limit 20000000").map(row=>{
      val json = row.get(0).toString
      val uuid1 = UUID.randomUUID()
      val uuid2 = UUID.randomUUID()
      val uuid3 = UUID.randomUUID()
      (json,uuid1,uuid2,uuid3)
    }).toDF("json","uuid1","uuid2","uuid3")
      .write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable("newnobench")
  }

}
