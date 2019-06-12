package com.fox.examples.sql


import java.util.UUID

import com.fox.examples.schema.{ods_pcic_dmc_model_data}
import com.fox.examples.util.CreateTableCLI
import org.apache.commons.cli.HelpFormatter
import org.apache.spark.sql.SparkSession
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
  * @author zyp
  */
object CreateTable {

//val logger:Logger = LoggerFactory.getLogger(this.getClass)


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
      .master("local")
      .config("spark.sql.catalogImplementation", "hive")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._

    val sourceFile = commandLine.getOptionValue("s")
    val tableSourcePath = "resources/"+sourceFile
//    logger.info(s"CreateTable:$tableSourcePath")
    val tableName = commandLine.getOptionValue("t")



    val partitionNumber = Integer.parseInt(commandLine.getOptionValue("pn"))
    val recordEachPartition = Integer.parseInt(commandLine.getOptionValue("rep"))

    assert(sourceFile != null && tableName != null && partitionNumber != null && recordEachPartition != null)

    val baseData = Source.fromFile(tableSourcePath).mkString
    val seq =for (i <- 0 until partitionNumber) yield baseData

   tableName match {
      case "ods_pcic_dmc_model_data" =>
        val dataTable = spark.sparkContext.parallelize(seq,partitionNumber).mapPartitions(iter =>{
          val base = iter.next()
          val buffer = new ListBuffer[ods_pcic_dmc_model_data]
          val array = base.split("%%%%%")
          val tnt_inst_id = array(0)
          val id = array(1)
          val gmt_create = array(2)
          val gmt_creator = array(3)
          val gmt_modified = array(4)
          val gmt_modifier = array(5)
          val category = array(6)
          val entity_type = array(7)
          val entity_name = array(8)
          val entity_code = array(9)
          val data_provider = array(10)
          val data_time = array(11)
          val object_content = array(12)
          val metadata = array(13)
          val data_org_id = array(14)
          val collect_execution_id = array(15)
          for (i <- 0 until recordEachPartition) {
             buffer += ods_pcic_dmc_model_data(tnt_inst_id,id ,gmt_create ,gmt_creator ,gmt_modified ,gmt_modifier ,
               category ,entity_type ,entity_name ,entity_code ,data_provider ,data_time ,object_content ,metadata ,
               data_org_id ,collect_execution_id )
          }
          buffer.iterator
        }).toDF
        dataTable.write.format("hive").mode("append").option("fileFormat", "orc").saveAsTable(tableName)
      case "ods_pdm_order_operate" =>
      case "ods_lnia_org_info" =>
      case "ods_parm_d" =>
      case "ods_parm_d2" =>
      case "s_gd_poi_base" =>
      case "cms_ces_generic_review_df" =>
      case "s_generic_task_edit_result_json" =>
    }







  }
}
