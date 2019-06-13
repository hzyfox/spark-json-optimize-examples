package com.fox.examples.sql

import java.io.{BufferedReader, InputStreamReader}

import com.fox.examples.schema._
import com.fox.examples.util.CreateTableCLI
import org.apache.commons.cli.HelpFormatter
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
  * @author zyp
  */
object CreateTable1 {

  //val logger:Logger = LoggerFactory.getLogger(this.getClass)


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
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._


    val tableSourcePath = commandLine.getOptionValue("s")
    println(s"CreateTable:$tableSourcePath")


    val tableName = tableSourcePath.split("%%%")(1)
    assert(tableName != "")
    println(s"tableName:$tableName")


    val partitionNumber = Integer.parseInt(commandLine.getOptionValue("pn"))
    val recordEachPartition = Integer.parseInt(commandLine.getOptionValue("rep"))

    assert(tableSourcePath != null && tableName != null && partitionNumber > 0 && recordEachPartition > 0)

    val baseData = Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream(tableSourcePath)).mkString


    println(baseData)
    val seq = for (_ <- 0 until partitionNumber) yield baseData

    tableName match {

      case "dsp_cp_sdk_events" =>
        val dataTable = spark.sparkContext.parallelize(seq, partitionNumber).mapPartitions(iter => {
          val base = iter.next()
          val buffer = new ListBuffer[dsp_cp_sdk_events]
          val array = base.split("%%%%%")
          val content = array(0)
          for (_ <- 0 until recordEachPartition) {
            buffer += dsp_cp_sdk_events(content)
          }
          buffer.iterator
        }).toDF()
        dataTable.write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable(tableName)

      case "wifi_vac_log_internal" =>
        val dataTable = spark.sparkContext.parallelize(seq, partitionNumber).mapPartitions(iter => {
          val base = iter.next()
          val buffer = new ListBuffer[wifi_vac_log_internal]
          val array = base.split("%%%%%")
          val apipath = array(0)
          val loglevel = array(1)
          val jsondata = array(2)
          for (_ <- 0 until recordEachPartition) {
            buffer += wifi_vac_log_internal(apipath, loglevel, jsondata)
          }
          buffer.iterator
        }).toDF()
        dataTable.write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable(tableName)
      case "odl_cactus_scalog_di" =>
        val dataTable = spark.sparkContext.parallelize(seq, partitionNumber).mapPartitions(iter => {
          val base = iter.next()
          val buffer = new ListBuffer[odl_cactus_scalog_di]
          val array = base.split("%%%%%")
          val seedurl = array(0)
          val occurtime = array(1)
          val vultype = array(2)
          val description = array(3)
          for (_ <- 0 until recordEachPartition) {
            buffer += odl_cactus_scalog_di(seedurl, occurtime, vultype, description)
          }
          buffer.iterator
        }).toDF()
        dataTable.write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable(tableName)
      case "odps_gongxiang_monitor_data" =>
        val dataTable = spark.sparkContext.parallelize(seq, partitionNumber).mapPartitions(iter => {
          val base = iter.next()
          val buffer = new ListBuffer[odps_gongxiang_monitor_data]
          val array = base.split("%%%%%")
          val ts = array(0)
          val host_name = array(1)
          val full_parent_name = array(2)
          val item_id = array(3).toLong
          val item_name = array(4)
          val value = array(5)
          for (_ <- 0 until recordEachPartition) {
            buffer += odps_gongxiang_monitor_data(ts, host_name, full_parent_name, item_id, item_name, value)
          }
          buffer.iterator
        }).toDF()
        dataTable.write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable(tableName)

      case "toutiao_all_feeds" =>
        val dataTable = spark.sparkContext.parallelize(seq, partitionNumber).mapPartitions(iter => {
          val base = iter.next()
          val buffer = new ListBuffer[toutiao_all_feeds]
          val array = base.split("%%%%%")
          val feed_id = array(0)
          val form = array(1)
          val show_date = array(2)
          val feature = array(3)
          for (_ <- 0 until recordEachPartition) {
            buffer += toutiao_all_feeds(feed_id, form, show_date, feature)
          }
          buffer.iterator
        }).toDF()
        dataTable.write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable(tableName)

      case "shoutao_toutiao_feed_online_7days" =>
        val dataTable = spark.sparkContext.parallelize(seq, partitionNumber).mapPartitions(iter => {
          val base = iter.next()
          val buffer = new ListBuffer[shoutao_toutiao_feed_online_7days]
          val array = base.split("%%%%%")
          val feed_id = array(0)
          val name = array(1)
          for (_ <- 0 until recordEachPartition) {
            buffer += shoutao_toutiao_feed_online_7days(feed_id, name)
          }
          buffer.iterator
        }).toDF()
        dataTable.write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable(tableName)
      case "gul_tpp_tt_feature_log_mm" =>
        val dataTable = spark.sparkContext.parallelize(seq, partitionNumber).mapPartitions(iter => {
          val base = iter.next()
          val buffer = new ListBuffer[gul_tpp_tt_feature_log_mm]
          val array = base.split("%%%%%")
          val pvid = array(0)
          val timestamp = array(1)
          val user_json = array(2)
          for (_ <- 0 until recordEachPartition) {
            buffer += gul_tpp_tt_feature_log_mm(pvid, timestamp, user_json)
          }
          buffer.iterator
        }).toDF()
        dataTable.write.format("hive").mode("overwrite").option("fileFormat", "orc").saveAsTable(tableName)
      case _ =>
        throw new IllegalArgumentException(s"illegal tablename: $tableName")
    }

    spark.sql(s"select * from $tableName").show(10)
  }
}

