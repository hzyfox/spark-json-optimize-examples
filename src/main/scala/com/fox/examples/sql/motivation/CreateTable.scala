package com.fox.examples.sql.motivation

import com.fox.examples.schema.NoBench
import org.apache.spark.sql.SparkSession

import scala.io.Source

/**
  * create with com.fox.examples.sql.motivation
  * USER: husterfox
  */
object CreateTable {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .config("spark.sql.catalogImplementation", "hive")
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    spark.sparkContext.textFile("hdfs://mycluster/user/spark/zyp/nobench_data.json").filter(line => line != "[" && line != "]" && line.nonEmpty).
      map(line => if(line.last == ',') line.substring(0,line.length-1)else line)
      .map(NoBench).toDF().write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable("NoBench")
     spark.sql("select get_json_object(nobench_json, '$.str1'), * from NoBench limit 10").show(10)
  }
}
