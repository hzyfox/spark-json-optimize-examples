package com.fox.examples.sql

import com.fox.examples.util.CreateTableCLI
import org.apache.commons.cli.HelpFormatter
import org.apache.spark.sql.SparkSession

/**
  * @author zyp
  */
object TestSQL {
  def main(args: Array[String]): Unit = {
    val helper = "TestSQl:"+
      " \n -h or --help to view helper"

    val (commandLine, options) = CreateTableCLI.parseCommandLine(args)
    val formatter = new HelpFormatter()

    if (commandLine.hasOption("h")) {
      CreateTableCLI.printHelpAndExit(formatter, options, helper, -1)
    }



    val sqlNumber = commandLine.getOptionValue("ssn")
    val optimize = commandLine.getOptionValue("o")
    assert(sqlNumber != null && optimize != null)

    val spark = SparkSession
      .builder()
      .master("local")
      .config("spark.sql.catalogImplementation","hive")
      //      .config("spark.sql.json.optimize",true)

      //      .config("spark.sql.codegen.wholeStage", false)
      .enableHiveSupport()
      .getOrCreate()

  }
}
