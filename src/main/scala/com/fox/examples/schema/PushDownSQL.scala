package com.fox.examples.schema

/**
  * @author zyp
  */
object PushDownSQL {

  val testSQL1 =
    """
      |SELECT get_json_object(nobench_json,'$.str1'), get_json_object(nobench_json,'$.num') FROM NoBench
    """.stripMargin


  val testSQL2 =
    """
      |select get_json_object(nobench_json,'$.nested_obj.str1'),get_json_object(nobench_jsonm,'$.nested_obj.num') from NoBench
    """.stripMargin


  val testSQL3 =
    """
      |SELECT get_json_object(nobench_json,'$.sparse_110'), get_json_object(nobench_json,'$.sparse_119') FROM NoBench
    """.stripMargin


  val testSQL4 =
    """
      |SELECT get_json_object(nobench_json,'$.sparse_110'), get_json_object(nobench_json,'$.sparse_120') FROM NoBench
    """.stripMargin


  val testSQl5 =
    """
      |select * from nobench where get_json_object(nobench_json,'$.str1') = 'GBRDCMJQGAYTCMBQGEYDCMJQGEYTCMBRGAYTA==='
    """.stripMargin

  val testSQl6 =
    """
      |select * from nobench where get_json_object(nobench_json,'$.num') between 1 and 10000
    """.stripMargin

  val testSQl7 =
    """
      |select * from nobench where get_json_object(nobench_json,'$.dyn1') between '10000' and '20000'
    """.stripMargin

  val testSQL8 =
    """
      |SELECT COUNT(*), get_json_object(nobench_json,'$.thousandth') as thousandth FROM NoBench WHERE get_json_object(nobench_json,'$.num') BETWEEN
      |1 AND 10000 GROUP BY thousandth
    """.stripMargin

  val testSQL9 =
    """
      |select * from (SELECT get_json_object(nobench_json, '$.num') as num, get_json_object(nobench_json, '$.nested_obj.str') as str FROM NoBench) left1 inner join
      |(SELECT get_json_object(nobench_json, '$.str1') as str1 from NoBench) right1 ON (left1.str =
      |right1.str1) WHERE left1.num BETWEEN 1 AND 10000
    """.stripMargin


 val testSQL10 =
   """
     |select * from nobench where get_json_object(nobench_json,'$.nested_arr[0]') = 'out'
   """.stripMargin



}
