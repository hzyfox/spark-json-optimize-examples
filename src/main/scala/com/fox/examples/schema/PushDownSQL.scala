package com.fox.examples.schema

/**
  * @author zyp
  */
object PushDownSQL {

  val testSQL1 =
    """
      |SELECT get_json_object(nobench_json,'$.str1'), get_json_object(nobench_json,'$.num') from newnobench
    """.stripMargin


  val testSQL2 =
    """
      |select get_json_object(nobench_json,'$.nested_obj.str1'),get_json_object(nobench_jsonm,'$.nested_obj.num') from newnobench
    """.stripMargin


  val testSQL3 =
    """
      |SELECT get_json_object(nobench_json,'$.sparse_340'), get_json_object(nobench_json,'$.sparse_341') from newnobench
    """.stripMargin


  val testSQL4 =
    """
      |SELECT get_json_object(nobench_json,'$.sparse_340'), get_json_object(nobench_json,'$.sparse_470') from newnobench
    """.stripMargin


  /***********************************分割线5-10都有过滤条件**********************************************/


  val testSQl5 =
   """
     |select nobench_json_str1,uuid1,uuid2,uuid3 from default_newnobench where nobench_json_str1 = 'GBRDCMJQGAYTCMBQGEYDCMJQGEYTCMBRGAYTA==='
   """.stripMargin
  val sql5 =
      """
        |select get_json_object(nobench_json,'$.str1'),uuid1,uuid2,uuid3 from newnobench where get_json_object(nobench_json,'$.str1') = 'GBRDCMJQGAYTCMBQGEYDCMJQGEYTCMBRGAYTA==='
      """.stripMargin

  val sql6 =
    """
      |select get_json_object(nobench_json,'$.num'),uuid1,uuid2,uuid3 from newnobench where get_json_object(nobench_json,'$.num') between 1 and 10000 order by get_json_object(nobench_json,'$.num')
    """.stripMargin
val testSQl6 =
    """
      |select nobench_json_num,uuid1,uuid2,uuid3 from default_newnobench where nobench_json_num between 1 and 10000 order by nobench_json_num
    """.stripMargin

  val sql7 =
    """
      |select get_json_object(nobench_json,'$.dyn1'),uuid1,uuid2,uuid3  from newnobench where get_json_object(nobench_json,'$.dyn1') between 10000 and 20000 order by get_json_object(nobench_json,'$.dyn1')
    """.stripMargin
val testSQl7 =
  """
    |select nobench_json_dyn1,uuid1,uuid2,uuid3  from default_newnobench where nobench_json_dyn1 between 10000 and 20000 order by nobench_json_dyn1
  """.stripMargin

  val testSQL8 =
    """
      |SELECT COUNT(*) as num , nobench_json_thousandth as thousandth,uuid1,uuid2,uuid3  FROM default_newnobench WHERE nobench_json_num BETWEEN
      |1 AND 10000 GROUP BY thousandth order by num
    """.stripMargin
  val sql8=
    """
      |SELECT COUNT(*) as num , get_json_object(nobench_json,'$.thousandth') as thousandth ,uuid1,uuid2,uuid3 from newnobench WHERE get_json_object(nobench_json,'$.num') BETWEEN
      |1 AND 10000 GROUP BY thousandth order by num
    """.stripMargin

  val testSQL9 =
    """
      |select * from (SELECT nobench_json_num as num, nobench_json_nested_obj_str as str FROM default_newnobench) left1 inner join
      |(SELECT nobench_json_str1 as str1,uuid1,uuid2,uuid3  from default_newnobench) right1 ON (left1.str =
      |right1.str1) WHERE left1.num BETWEEN 1 AND 10000
    """.stripMargin
  val sql9 =
    """
      |select * from (SELECT get_json_object(nobench_json, '$.num') as num, get_json_object(nobench_json, '$.nested_obj.str') as str from newnobench) left1 inner join
      |(SELECT get_json_object(nobench_json, '$.str1') as str1 ,uuid1,uuid2,uuid3 from newnobench) right1 ON (left1.str =
      |right1.str1) WHERE left1.num BETWEEN 1 AND 10000
    """.stripMargin


 val testSQL10 =
   """
     |select nobench_json_nested_arr0 from default_newnobench where nobench_json_nested_arr0 = 'out'
   """.stripMargin
  val sql10 =
   """
     |select get_json_object(nobench_json,'$.nested_arr[0]') from newnobench where get_json_object(nobench_json,'$.nested_arr[0]') = 'out'
   """.stripMargin



}
