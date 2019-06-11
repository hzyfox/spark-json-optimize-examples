package com.fox.examples.sql


import com.fox.examples.util.CreateTableCLI
import org.apache.commons.cli.HelpFormatter
import org.apache.spark.sql.SparkSession
import org.slf4j.{Logger, LoggerFactory}

/**
  * @author zyp
  */
object CreateTable {

val logger:Logger = LoggerFactory.getLogger(this.getClass)


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
      .enableHiveSupport()
      .getOrCreate()

    val sourceFile = commandLine.getOptionValue("s")
    val tableSourcePath = "resources/"+sourceFile
    logger.info(s"CreateTable:$tableSourcePath")
    val tableName = commandLine.getOptionValue("t")



    val partitionNumber = Integer.parseInt(commandLine.getOptionValue("pn"))
    val recordEachPartition = Integer.parseInt(commandLine.getOptionValue("rep"))

    assert(sourceFile != null && tableName != null && partitionNumber != null && recordEachPartition != null)


   spark.sparkContext.textFile(tableSourcePath)


  }
}
