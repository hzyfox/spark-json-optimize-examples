package com.fox.examples.sql

import org.apache.hadoop.hive.ql.io.sarg.SearchArgumentFactory

/**
  * @author zyp
  */
object PushDown {
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

  val sarg_5 = SearchArgumentFactory.newBuilder.startAnd.equals("nobench_json_str1",sql5_str1.toString).end().build().toKryo
  val sarg_6 = SearchArgumentFactory.newBuilder().startAnd().between("nobench_json_num",sql6_num_min.toLong,sql6_num_max.toLong).end().build().toKryo
  val sarg_7 = SearchArgumentFactory.newBuilder().startAnd().between("nobench_json_dyn1",sql7_dyn1_min.toLong,sql7_dyn1_max.toLong).end().build().toKryo
  val sarg_8 = SearchArgumentFactory.newBuilder().startAnd().between("nobench_json_num",sql8_num_min.toLong,sql8_num_max.toLong).end().build().toKryo
  val sarg_9 = sarg_8
  val sarg_10 = SearchArgumentFactory.newBuilder().startAnd().equals("")



}
