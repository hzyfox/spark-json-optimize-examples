package com.fox.examples.sql

import java.util.Date

import com.fox.examples.schema.{AllSQL1, MisonSQL}
import com.fox.examples.util.CreateTableCLI
import org.apache.commons.cli.HelpFormatter
import org.apache.spark.sql.SparkSession

/**
  * @author zyp
  */
object TestSQL1 {


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

    if(optimize.equals("mison")){
      val spark = SparkSession
        .builder()
        .config("spark.sql.catalogImplementation", "hive")
        .enableHiveSupport()
        .getOrCreate()

      val AllMisonSQL = Array(MisonSQL.sql1,MisonSQL.sql2,MisonSQL.sql3,MisonSQL.sql4,MisonSQL.sql5,MisonSQL.sql6,MisonSQL.sql7,MisonSQL.sql8,MisonSQL.sql9,MisonSQL.sql10)
      sqlNumber match{
        case n if n >= 1 && n <= 10 =>{
          var st = new Date().getTime
          spark.sql(AllMisonSQL(n - 1)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          var et = new Date().getTime
          println(s"TestSQL $n: First execution time = ${(et - st) / 1000.0}s ")

          st = new Date().getTime
          for (i <- 0 until cycleNumber) {
            spark.sql(AllMisonSQL(n - 1)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          }
          et = new Date().getTime
          println(s"TestSQL $n:$cycleNumber times average time = ${(et - st) / (cycleNumber * 1000.0)}s")
          spark.sql(AllMisonSQL(n-1)).show(10)
        }
        case t =>
          throw new IllegalArgumentException(s"illegal number: $t")
      }

    }else{

      val spark = SparkSession
        .builder()
        .config("spark.sql.catalogImplementation", "hive")
        .config("spark.sql.json.optimize", optimize)
        .enableHiveSupport()
        .getOrCreate()
      val AllSQLs = Array( AllSQL1.sql1, AllSQL1.sql2,AllSQL1.sql3,AllSQL1.sql4,AllSQL1.sql5,AllSQL1.sql6,AllSQL1.sql7, AllSQL1.sql8, AllSQL1.sql9, AllSQL1.sql10)
      sqlNumber match {
        case n if n >= 1 && n <= 10 =>
          var st = new Date().getTime
          println(s"******************************************************************$n")
          spark.sql(AllSQLs(n-1)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          var et = new Date().getTime
          println(s"TestSQL $n: First execution time = ${(et - st) / 1000.0}s ")

          st = new Date().getTime
          for (i <- 0 until cycleNumber) {
            spark.sql(AllSQLs(n-1)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          }
          et = new Date().getTime
          println(s"TestSQL $n:$cycleNumber times average time = ${(et - st) / (cycleNumber * 1000.0)}s")
          spark.sql(AllSQLs(n-1)).show(10)
        case t =>
          throw new IllegalArgumentException(s"illegal number: $t")
      }
    }
  }
}

