package com.fox.examples.util

import java.nio.charset.Charset
import com.fasterxml.jackson.databind.DeserializationFeature

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.beans.BeanProperty
import java.util

import scala.collection.JavaConversions._

/**
  * create with com.fox.examples.util
  * USER: husterfox
  */
object ParseTableSchema {

  case class Item(@JsonProperty("name") @BeanProperty name: String, @JsonProperty("type") `type`: String, @JsonProperty("comment") comment: String, @JsonProperty("id") id: String)

  case class Outer(@JsonProperty("tableName") tableName: String, @JsonProperty("cols") cols: util.ArrayList[Item])

  def main(args: Array[String]): Unit = {
    //把schema 复制过来 即可生成建表语句
    val schema0 =
      "{\n  \"tableName\": \"gul_tpp_tt_feature_log_mm\",\n  \"tableId\": \"e440bcae0a9841bc951fadfe60e11cc4\",\n  \"dbName\": \"palgo_gul\",\n  \"owner\": \"1005279957117255\",\n  \"createTime\": 1532271585,\n  \"lastAccessTime\": 0,\n  \"retention\": 0,\n  \"sd\": {\n    \"compressed\": false,\n    \"numBuckets\": -1,\n    \"bucketColOrders\": []\n  },\n  \"partitionKeys\": [\n    {\n      \"name\": \"ds\",\n      \"type\": \"string\",\n      \"comment\": \"\",\n      \"id\": \"ddd986732db04353afdba3ddb6011fa4\"\n    },\n    {\n      \"name\": \"hh\",\n      \"type\": \"string\",\n      \"comment\": \"\",\n      \"id\": \"143da2c1ad414546810ad8845d1357e6\"\n    },\n    {\n      \"name\": \"mm\",\n      \"type\": \"string\",\n      \"comment\": \"\",\n      \"id\": \"b0fff4ec348746bc820bb8f472d2843f\"\n    }\n  ],\n  \"parameters\": {\n    \"lifecycle\": \"30\",\n    \"last_modified_time\": \"1546619550\",\n    \"size\": \"8158452507560\",\n    \"transient_lastDdlTime\": \"1532271585\",\n    \"TABLE_TOTAL_PARTITIONS\": \"2925\",\n    \"file_num\": \"6881\",\n    \"is_odps_archived\": \"false\"\n  },\n  \"tableType\": \"MANAGED_TABLE\",\n  \"cols\": [\n    {\n      \"name\": \"pvid\",\n      \"type\": \"string\",\n      \"comment\": \"\",\n      \"id\": \"7f4514ce886c4cdd808139b7cca72756\"\n    },\n    {\n      \"name\": \"timestamp\",\n      \"type\": \"string\",\n      \"comment\": \"\",\n      \"id\": \"8b1140550ea341c08ba745872e79aa1e\"\n    },\n    {\n      \"name\": \"user_json\",\n      \"type\": \"string\",\n      \"comment\": \"用户特征map\",\n      \"id\": \"19e22ae492e3469b9d069c5def53a206\"\n    },\n    {\n      \"name\": \"item_json_list\",\n      \"type\": \"string\",\n      \"comment\": \"商品特征list\",\n      \"id\": \"02e6585a2c164cbdbcbf775eb5daea8a\"\n    }\n  ]\n}"
    val schema = schema0.replace("\n","")
    val mapper = new ObjectMapper()

    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.registerModules(DefaultScalaModule)
    //    val jsonTree = mapper.readTree(schema)
    //    println(jsonTree.path("tableName").asText())
    val jsonMapper = mapper.readValue(schema, classOf[Outer])
    println(
      s"""
         |create table ${jsonMapper.tableName}(
         |${
        var cols = ""
        var head = false
        for (item <- jsonMapper.cols) {
          if (!head) {
            head = true
            cols += s"${item.name} ${item.`type`}"
          } else {
            cols += s",\n${item.name} ${item.`type`}"
          }
        }
        cols
      })
     """.stripMargin)

  }

}
