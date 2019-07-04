package com.fox.examples.sql.motivation

import java.util.Date

import com.fox.examples.schema.AllSQL1
import com.fox.examples.util.CreateTableCLI
import org.apache.commons.cli.HelpFormatter
import org.apache.spark.sql.SparkSession

/**
  * create with com.fox.examples.sql.motivation
  * USER: husterfox
  */
object JsonCost {
  def main(args: Array[String]): Unit = {
    val helper = "TestSQl:" +
      " \n -h or --help to view helper"

    val (commandLine, options) = CreateTableCLI.parseCommandLine(args)
    val formatter = new HelpFormatter()

    if (commandLine.hasOption("h")) {
      CreateTableCLI.printHelpAndExit(formatter, options, helper, -1)
    }


    val sqlNumber = commandLine.getOptionValue("ssn").toInt
    val optimize = commandLine.getOptionValue("o")
    val cycleNumber = commandLine.getOptionValue("cn").toInt
    assert(sqlNumber > 0 && optimize != null && cycleNumber > 0)

    val spark = SparkSession
      .builder()
      .config("spark.sql.catalogImplementation", "hive")
      .config("spark.sql.json.optimize", optimize)
      .enableHiveSupport()
      .getOrCreate()
    val AllSQLs = Array(AllSQL1.testSQL1, AllSQL1.testSQL2, AllSQL1.testSQL3)
    sqlNumber match {
      case n if n >= 1 && n <= 3 =>
        spark.sql(AllSQLs(n - 1)).count()
        spark.sql(AllSQLs(n-1)).count()
      case t =>
        throw new IllegalArgumentException(s"illegal number: $t")
    }
  }
}
