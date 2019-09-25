package com.fox.examples.sql

import java.text.{ParsePosition, SimpleDateFormat}
import java.util

import scala.collection.mutable.ArrayBuffer

/**
  * @author zyp
  */
object GenarateSQl {
  def main(args: Array[String]): Unit = {
    val date = "20190110"
    val times = Array(s"$date 00:00:00"
      ,s"$date 01:00:00"
      ,s"$date 02:00:00"
      ,s"$date 03:00:00"
      ,s"$date 04:00:00"
      ,s"$date 05:00:00"
      ,s"$date 06:00:00"
      ,s"$date 07:00:00"
      ,s"$date 08:00:00"
      ,s"$date 09:00:00"
      ,s"$date 10:00:00"
      ,s"$date 11:00:00"
      ,s"$date 12:00:00"
      ,s"$date 13:00:00"
      ,s"$date 14:00:00"
      ,s"$date 15:00:00"
      ,s"$date 16:00:00"
      ,s"$date 17:00:00"
      ,s"$date 18:00:00"
      ,s"$date 19:00:00"
      ,s"$date 20:00:00"
      ,s"$date 21:00:00"
      ,s"$date 22:00:00"
      ,s"$date 23:00:00"
      ,s"$date 24:00:00")

    var timeStamp = new ArrayBuffer[String]()

    for(t <- times){
      val time = (new SimpleDateFormat("yyyyMMdd HH:mm:ss")).parse(t, new ParsePosition(0)).getTime() / 1000;
      System.out.println("获取指定时间戳："+time);
      timeStamp += time.toString
    }

    val sql = s"""select
    count(case when start_time >= ${timeStamp(0)} and start_time < ${timeStamp(1)} then start_time end ) as 0_1,
    count(case when start_time >= ${timeStamp(1)} and start_time < ${timeStamp(2)} then start_time end ) as 1_2,
    count(case when start_time >= ${timeStamp(2)} and start_time < ${timeStamp(3)} then start_time end ) as 2_3,
    count(case when start_time >= ${timeStamp(3)} and start_time < ${timeStamp(4)} then start_time end ) as 3_4,
    count(case when start_time >= ${timeStamp(4)} and start_time < ${timeStamp(5)} then start_time end ) as 4_5,
    count(case when start_time >= ${timeStamp(5)} and start_time < ${timeStamp(6)} then start_time end ) as 5_6,
    count(case when start_time >= ${timeStamp(6)} and start_time < ${timeStamp(7)} then start_time end ) as 6_7,
    count(case when start_time >= ${timeStamp(7)} and start_time < ${timeStamp(8)} then start_time end ) as 7_8,
    count(case when start_time >= ${timeStamp(8)} and start_time < ${timeStamp(9)} then start_time end ) as 8_9,
    count(case when start_time >= ${timeStamp(9)} and start_time < ${timeStamp(10)} then start_time end ) as 9_10,
    count(case when start_time >= ${timeStamp(10)} and start_time < ${timeStamp(11)} then start_time end ) as 10_11,
    count(case when start_time >= ${timeStamp(11)} and start_time < ${timeStamp(12)} then start_time end ) as 11_12,
    count(case when start_time >= ${timeStamp(12)} and start_time < ${timeStamp(13)} then start_time end ) as 12_13,
    count(case when start_time >= ${timeStamp(13)} and start_time < ${timeStamp(14)} then start_time end ) as 13_14,
    count(case when start_time >= ${timeStamp(14)} and start_time < ${timeStamp(15)} then start_time end ) as 14_15,
    count(case when start_time >= ${timeStamp(15)} and start_time < ${timeStamp(16)} then start_time end ) as 15_16,
    count(case when start_time >= ${timeStamp(16)} and start_time < ${timeStamp(17)} then start_time end ) as 16_17,
    count(case when start_time >= ${timeStamp(17)} and start_time < ${timeStamp(18)} then start_time end ) as 17_18,
    count(case when start_time >= ${timeStamp(18)} and start_time < ${timeStamp(19)} then start_time end ) as 18_19,
    count(case when start_time >= ${timeStamp(19)} and start_time < ${timeStamp(20)} then start_time end ) as 19_20,
    count(case when start_time >= ${timeStamp(20)} and start_time < ${timeStamp(21)} then start_time end ) as 20_21,
    count(case when start_time >= ${timeStamp(21)} and start_time < ${timeStamp(22)} then start_time end ) as 21_22,
    count(case when start_time >= ${timeStamp(22)} and start_time < ${timeStamp(23)} then start_time end ) as 22_23,
    count(case when start_time >= ${timeStamp(23)} and start_time < ${timeStamp(24)} then start_time end ) as 23_24
    from meta.m_instance where ds = $date and (instr(source_xml,'GET_JSON_OBJECT') > 0
    or instr(source_xml,'get_json_object') >0)
    limit 30; """

    println(sql)

  }

}
