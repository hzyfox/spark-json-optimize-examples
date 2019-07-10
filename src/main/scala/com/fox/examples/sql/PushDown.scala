package com.fox.examples.sql

import java.util.Date

import com.fox.examples.schema.PushDownSQL
import com.fox.examples.util.CreateTableCLI
import org.apache.commons.cli.HelpFormatter
import org.apache.hadoop.hive.ql.io.sarg.SearchArgumentFactory
import org.apache.spark.sql.SparkSession

/**
  * @author zyp
  */
object PushDown {
  /** 每条语句要读的column **/

  val col5 = "nobench_json_str1"
  val col6 = "nobench_json_num"
  val col7 = "nobench_json_dyn1"
  val col8 = "nobench_json_thousandth,nobench_json_num"
  val col9 = "nobench_json_num,nobench_json_nested_obj_str,nobench_json_str1"
  val col10 = "nobench_json_nested_arr0"


  /** 过滤条件 **/
  val sql5_str1 = "GBRDCMJQGAYTCMBQGEYDCMJQGEYTCMBRGAYTA==="

  val sql6_num_min = "20000"
  val sql6_num_max = "30000"

  val sql7_dyn1_min = "10000"
  val sql7_dyn1_max = "20000"

  val sql8_num_min = "1"
  val sql8_num_max = "10000"

  val sql9_num_min = "1"
  val sql9_num_max = "10000"

  val sql10_out = "out"

  val sarg_5 = SearchArgumentFactory.newBuilder.startAnd.equals("nobench_json_str1", sql5_str1.toString).end().build().toKryo
  val sarg_6 = SearchArgumentFactory.newBuilder().startAnd().between("nobench_json_num", sql6_num_min.toLong, sql6_num_max.toLong).end().build().toKryo
  val sarg_7 = SearchArgumentFactory.newBuilder().startAnd().between("nobench_json_dyn1", sql7_dyn1_min.toLong, sql7_dyn1_max.toLong).end().build().toKryo
  val sarg_8 = SearchArgumentFactory.newBuilder().startAnd().between("nobench_json_num", sql8_num_min.toLong, sql8_num_max.toLong).end().build().toKryo
  val sarg_9 = sarg_8
  val sarg_10 = SearchArgumentFactory.newBuilder().startAnd().equals("nobench_json_nested_arr0", sql10_out.toString).end().build().toKryo

  def main(args: Array[String]): Unit = {
    val helper = "TestSQl:" +
      " \n -h or --help to view helper"

    val (commandLine, options) = CreateTableCLI.parseCommandLine(args)
    val formatter = new HelpFormatter()

    if (commandLine.hasOption("h")) {
      CreateTableCLI.printHelpAndExit(formatter, options, helper, -1)
    }

    val sqlNumber = commandLine.getOptionValue("ssn").toInt
    val optimize = commandLine.getOptionValue("o").toBoolean
    val cycleNumber = commandLine.getOptionValue("cn").toInt
    assert(sqlNumber != null && cycleNumber >= 0)

    val AllCol = Array(col5, col6, col7, col8, col9, col10)
    val AllArg = Array(sarg_5, sarg_6, sarg_7, sarg_8, sarg_9, sarg_10)
    val AllSqls = Array(PushDownSQL.testSQL1, PushDownSQL.testSQL2, PushDownSQL.testSQL3, PushDownSQL.testSQL4,
      PushDownSQL.testSQl5, PushDownSQL.testSQl6, PushDownSQL.testSQl7, PushDownSQL.testSQL8, PushDownSQL.testSQL9, PushDownSQL.testSQL10)

    if(optimize) {
      sqlNumber match {
        case n if n >= 1 && n <= 4 =>
          var st = new Date().getTime
          val spark = SparkSession.builder()
            .config("spark.sql.catalogImplementation", "hive")
            .enableHiveSupport()
            .getOrCreate()
          spark.sql(AllSqls(n-1)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          var et = new Date().getTime
          println(s"TestSQL $n: First execution time = ${(et - st) / 1000.0}s ")

          st = new Date().getTime
          for (i <- 0 until cycleNumber) {
            spark.sql(AllSqls(n-1)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          }
          et = new Date().getTime
          println(s"TestSQL $n:$cycleNumber times average time = ${(et - st) / (cycleNumber * 1000.0)}s")
          spark.sql(AllSqls(n-1)).show(10)
        case n if n >= 5 && n <= 10 =>
          var st = new Date().getTime
          val spark = SparkSession.builder()
            .config("spark.sql.catalogImplementation", "hive")
            .config("spark.sql.orc.filterPushdown", true)
            .config("hive.optimize.index.filter", true)
            .config("sarg.pushdown", AllArg(n - 5))
            .config("hive.io.file.readcolumn.names", AllCol(n - 5))
            .enableHiveSupport()
            .getOrCreate()
          print("***************************************")
          print(AllSqls(n-1))
          print("***************************************")

          spark.sql(AllSqls(n-1)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          var et = new Date().getTime
          println(s"TestSQL $n: First execution time = ${(et - st) / 1000.0}s ")

          st = new Date().getTime
          for (i <- 0 until cycleNumber) {
            spark.sql(AllSqls(n-1)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          }
          et = new Date().getTime
          println(s"TestSQL $n:$cycleNumber times average time = ${(et - st) / (cycleNumber * 1000.0)}s")
          spark.sql(AllSqls(n-1)).show(10)


        case t =>
          throw new IllegalArgumentException(s"illegal number: $t")
      }
    }else{
      val NotPushDownSql =  Array(PushDownSQL.sql5,
        PushDownSQL.sql6,
        PushDownSQL.sql7,
        PushDownSQL.sql8,
        PushDownSQL.sql9,
        PushDownSQL.sql10)
      sqlNumber match {
        case n if n >= 5 && n <= 10 =>
          var st = new Date().getTime
          val spark = SparkSession.builder()
            .config("spark.sql.catalogImplementation", "hive")
            .enableHiveSupport()
            .getOrCreate()
          spark.sql(NotPushDownSql(n-5)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          var et = new Date().getTime
          println(s"TestSQL $n: First execution time = ${(et - st) / 1000.0}s ")

          st = new Date().getTime
          for (i <- 0 until cycleNumber) {
            spark.sql(NotPushDownSql(n-5)).foreachPartition(iter => println(s"iter size:${iter.size}"))
          }
          et = new Date().getTime
          println(s"TestSQL $n:$cycleNumber times average time = ${(et - st) / (cycleNumber * 1000.0)}s")
          spark.sql(AllSqls(n-5)).show(10)

        case t =>
          throw new IllegalArgumentException(s"illegal number: $t")
      }

    }


  }


}
