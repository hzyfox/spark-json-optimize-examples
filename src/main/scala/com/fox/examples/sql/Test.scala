package com.fox.examples.sql

import java.util.UUID

import scala.util.Random

/**
  * @author zyp
  */
object Test {
  def main(args: Array[String]): Unit = {
    val string= "{{},{}}"
    val random = new Random().nextInt().toString
    val id = s""","random":"$random"}"""
    val x = string.substring(0,string.lastIndexOf("}"))+id
    println(x)
  }

}
