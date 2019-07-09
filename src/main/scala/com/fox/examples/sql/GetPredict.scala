package com.fox.examples.sql

import java.io.{File, PrintWriter}

import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * @author zyp
  */
object GetPredict {
  def main(args: Array[String]): Unit = {
    val filePath = "jsonpath/alljsonpath"
    val jsonpath = Source.fromInputStream(this.getClass.getClassLoader
      .getResourceAsStream(filePath)).getLines().map(x => {
      x.substring(x.indexOf(",")+1).replace(",","@")
    }).toList

    val allPredictJsonpath = Source.fromFile("C:\\Users\\zyp\\ali\\lstm_predict_set.txt").getLines()
    val writer = new PrintWriter(new File("jsonpath.txt" ))
    for(iter <- allPredictJsonpath){
      val subString = iter.substring(iter.indexOf("@")+1,iter.indexOf("^^"))
      if(jsonpath.contains(subString)){
        writer.write(iter)
        writer.write("\n")
      }
    }
    writer.close()
  }

}
