package com.fox.examples.sql

import java.text.SimpleDateFormat
import java.util.Date

/**
  * @author zyp
  */
object TimeStamp {
  def main(args: Array[String]): Unit = {
    val time:Long =1567879680
//    1546882667
//    1567879680
   val fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time*1000)
    println(fm)

  }

}
