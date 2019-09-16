package com.fox.examples.schema

/**
  * create with com.fox.examples.schema
  * USER: husterfox
  */
object AllSQL1 {

  val sql3 = """select
               |    parm_t_code,
               |    GET_JSON_OBJECT(json_data,'$.dataContentQualifierDesc') AS dataContentQualifierDesc,
               |    GET_JSON_OBJECT(json_data,'$.dataContentDepartmentId') AS  dataContentDepartmentId,
               |    GET_JSON_OBJECT(json_data,'$.dataContentDomainId') AS  dataContentDomainId,
               |    GET_JSON_OBJECT(json_data,'$.gmtModify') AS  gmtModify,
               |    GET_JSON_OBJECT(json_data,'$.ownerName') AS  ownerName,
               |    GET_JSON_OBJECT(json_data,'$.ModifierName') AS  ModifierName,
               |    GET_JSON_OBJECT(json_data,'$.dataContentQualifierEnName') AS  dataContentQualifierEnName,
               |    GET_JSON_OBJECT(json_data,'$.dataContentQualifierId') AS  dataContentQualifierId,
               |    GET_JSON_OBJECT(json_data,'$.gmtCreate') AS  gmtCreate,
               |    GET_JSON_OBJECT(json_data,'$.dataContentCompositeQualifierIds') AS  dataContentCompositeQualifierIds,
               |    GET_JSON_OBJECT(json_data,'$.dataContentQualifierCnName') AS  dataContentQualifierCnName,
               |     crtor,
               |     last_moder,
               |     gmt_create,
               |     gmt_modified,
               |     json_data
               |     from ods_parm_d
               |	where parm_t_code != '120001235'""".stripMargin

  val sql4 = """select
               |	'20190104' as thedate
               |	,parm_t_code
               |	,GET_JSON_OBJECT(json_data,'$.dataContentDimAttributeEnName') as dataContentDimAttributeEnName
               |	,GET_JSON_OBJECT(json_data,'$.dataContentDepartmentId') as dataContentDepartmentId
               |	,GET_JSON_OBJECT(json_data,'$.dataContentDimAttributeCnName') as dataContentDimAttributeCnName
               |	,GET_JSON_OBJECT(json_data,'$.dataContentDimId') as dataContentDimId
               |	,GET_JSON_OBJECT(json_data,'$.dataContentDimAttributeId') as dataContentDimAttributeId
               |	,GET_JSON_OBJECT(json_data,'$.dataClassification') as dataClassification
               |	,GET_JSON_OBJECT(json_data,'$.dataContentDimAttributeHierachyId') as dataContentDimAttributeHierachyId
               |	,GET_JSON_OBJECT(json_data,'$.dataContentDimAttributeDesc') as dataContentDimAttributeDesc
               |	,GET_JSON_OBJECT(json_data,'$.dataContentParentDimAttributeId') as dataContentParentDimAttributeId
               |	,GET_JSON_OBJECT(json_data,'$.dataContentDataType') as dataContentDataType
               |	,crtor
               |	,last_moder
               |	,gmt_create
               |	,gmt_modified
               |	,json_data
               |	  from ods_parm_d2
               |	where parm_t_code != '120001242'""".stripMargin

  val sql5 = """select aa.poiid,
               |	                aa.name,
               |	                aa.update_flag as newupdate_flag,
               |	                aa.update_flag_source as newupdate_flagsource,
               |                 aa.address,
               |                 aa.name_source,
               |                 aa.address_source,
               |                 aa.navi_source
               |	from
               |	(
               |	        SELECT poiid
               |	                , get_json_object(json_str,'$.update_flag') as update_flag
               |	                , get_json_object(json_str,'$.merged_status') as merged_status
               |	                , get_json_object(json_str,'$.baseinfo.name') as name
               |	                , get_json_object(json_str,'$.baseinfo.address') as address
               |	                ,get_json_object(json_str, '$.baseinfo.from_field.name')  as name_source
               |	                ,get_json_object(json_str, '$.baseinfo.from_field.address') as address_source
               |	                ,get_json_object(json_str, '$.baseinfo.from_field.navi') as navi_source
               |	                ,case
               |	                        when get_json_object(json_str,'$.update_flag') != 'd' or get_json_object(json_str,'$.merged_status') != '1' then get_json_object(json_str, '$.baseinfo.from.src_type')
               |	                        else get_json_object(json_str, '$.baseinfo.from_field.opt_type')
               |	                 end as update_flag_source
               |	        FROM s_gd_poi_base
               |
               |	) aa""".stripMargin


  val sql6 = """
               |	        select
               |	        GET_JSON_OBJECT(b.data, '$.result.poi_status.verify') as result_tag
               |	        from
               |	        s_generic_task_edit_result_json b
               |	   """.stripMargin

  /**
    * sql 7
    */

  val sql7 =
    """
      |select  coalesce(get_json_object(content, '$.params.accountId'), '') as account_id
      |,coalesce(get_json_object(content, '$.envInfo.devInfo.deviceId'), '') as dev_id
      |,coalesce(get_json_object(content, '$.envInfo.devInfo.brand'), '') as brand
      |,coalesce(get_json_object(content, '$.envInfo.devInfo.model'), '') as model
      |,coalesce(cast(get_json_object(content, '$.ts') as bigint), 0) as ts
      |,coalesce(get_json_object(content, '$.envInfo.devInfo.ip'), '') as ip
      |,coalesce(get_json_object(content, '$.envInfo.devInfo.os'), 'ios') as os_id
      |,coalesce(get_json_object(content, '$.appId'), '0') as appid
      |,'' as sdk_ch_id
      |,'APPSTORE' as ch_id
      |,1001 as biz_id
      |,coalesce(get_json_object(content, '$.event'), '') as event
      |,coalesce(get_json_object(content, '$.params'), '') as params
      |,coalesce(get_json_object(content, '$.envInfo'), '') as envinfo
      |,coalesce(get_json_object(content, '$.src'), '') as src
      |from
      |dsp_cp_sdk_events
    """.stripMargin

  val sql8 =
    """
      |SELECT loglevel AS notify_status,
      |GET_JSON_OBJECT(jsondata,"$.ap.ap_mac") AS ap_mac,
      |GET_JSON_OBJECT(jsondata,"$.ap.sw_version") AS version,
      |GET_JSON_OBJECT(jsondata,"$.ap.serial") AS serial,
      |GET_JSON_OBJECT(jsondata,"$.ap.status") AS status_code,
      |GET_JSON_OBJECT(jsondata,"$.ap.ap_model") AS ap_model,
      |GET_JSON_OBJECT(jsondata,"$.ap.sta_cnt") AS sta_cnt,
      |GET_JSON_OBJECT(jsondata,"$.ap.cpu_usage") AS cpu,
      |GET_JSON_OBJECT(jsondata,"$.ap.memory_usage") AS memory,
      |GET_JSON_OBJECT(jsondata,"$.ap.wan_recv_speed") AS rx_speed,
      |GET_JSON_OBJECT(jsondata,"$.ap.wan_sent_speed") AS tx_speed,
      |GET_JSON_OBJECT(jsondata,"$.ap.sw_version_verbose") AS sw_verbose,
      |GET_JSON_OBJECT(jsondata,"$.ap.last_reboot_reason") AS last_reboot_reason,
      |GET_JSON_OBJECT(jsondata,"$.ap.effect_md5") AS effect_md5,
      |GET_JSON_OBJECT(jsondata,"$.ap.ota_uuid") AS ota_uuid,
      |GET_JSON_OBJECT(jsondata,"$.ap.sync") AS sync_status,
      |GET_JSON_OBJECT(jsondata,"$.ap.radio[0].radio_index") AS radio_a,
      |GET_JSON_OBJECT(jsondata,"$.ap.radio[0].channel") AS channel_a,
      |GET_JSON_OBJECT(jsondata,"$.ap.radio[0].channel_usage") AS utilities_a,
      |GET_JSON_OBJECT(jsondata,"$.ap.radio[0].noise") AS noise_a,
      |GET_JSON_OBJECT(jsondata,"$.ap.radio[1].radio_index") AS radio_b,
      |GET_JSON_OBJECT(jsondata,"$.ap.radio[1].channel") AS channel_b,
      |GET_JSON_OBJECT(jsondata,"$.ap.radio[1].channel_usage") AS utilities_b,
      |GET_JSON_OBJECT(jsondata,"$.ap.radio[1].noise") AS noise_b,
      |GET_JSON_OBJECT(jsondata,"$.ap.link_speed") AS link_speed,
      |GET_JSON_OBJECT(jsondata,"$.ap.public_ip") AS public_ip,
      |GET_JSON_OBJECT(jsondata,"$.ap.wan_type") AS wan_type,
      |GET_JSON_OBJECT(jsondata,"$.ap.wan_recv") AS wan_recv,
      |GET_JSON_OBJECT(jsondata,"$.ap.wan_sent") AS wan_sent,
      |GET_JSON_OBJECT(jsondata,"$.wan_ip") AS wan_ip
      |FROM wifi_vac_log_internal
    """.stripMargin

  val sql9 =
    """
      |select seedurl as ip
      |, get_json_object(description, '$.weakUsername') as u
      |, get_json_object(description, '$.weakPassword') as p
      |from
      |odl_cactus_scalog_di
    """.stripMargin


  val sql10 =
    """
      |select ts,host_name,full_parent_name,item_id,item_name,
      |get_json_object(value,'$.cpu') cpu_avg,
      |get_json_object(value,'$.mem')mem_avg,
      |get_json_object(value,'$.load1')load1_avg,
      |get_json_object(value,'$.nginx_qps') nginx_qps_sum,
      |get_json_object(value,'$.nginx_rt') nginx_rt_avg
      |from odps_gongxiang_monitor_data
    """.stripMargin

  val sql11 =
    """
      |select feed_id,
      |'' as is_jingxuan,
      |'Y' is_zhuanti
      |from toutiao_all_feeds
      |union all
      |select feed_id,
      |if(get_json_object(feature,'$.isChoiceness')  = 'true', 'Y', 'N') as is_jingxuan,
      |'' as is_zhuanti
      |from toutiao_all_feeds
    """.stripMargin

  val sql12 =
    """
      |SELECT  pvid
      |,GET_JSON_OBJECT(user_json, '$.user_id') AS user_id
      |,timestamp
      |,GET_JSON_OBJECT(user_json, '$.g_cart') AS user__g_cart
      |,GET_JSON_OBJECT(user_json, '$.g_fav') AS user__g_fav
      |,GET_JSON_OBJECT(user_json, '$.g_pay') AS user__g_pay
      |,GET_JSON_OBJECT(user_json, '$.st') AS user__st
      |,GET_JSON_OBJECT(user_json, '$.time') AS user__time
      |,GET_JSON_OBJECT(user_json, '$.opt_seq_length') AS user__seq_length
      |,GET_JSON_OBJECT(user_json, '$.user_behavior_seq') AS user__user_behavior_seq
      |FROM    gul_tpp_tt_feature_log_mm
    """.stripMargin





  val testSQL1 =
    """
      |SELECT get_json_object(nobench_json,'$.str1'), get_json_object(nobench_json,'$.num') FROM NoBench
    """.stripMargin

  val testSQL2 =
    """
      |SELECT COUNT(*), get_json_object(nobench_json,'$.thousandth') as thousandth FROM NoBench WHERE get_json_object(nobench_json,'$.num') BETWEEN
      |1 AND 10000 GROUP BY thousandth
    """.stripMargin

  val testSQL3 =
    """
      |select * from (SELECT get_json_object(nobench_json, '$.num') as num, get_json_object(nobench_json, '$.nested_obj.str') as str FROM NoBench) left1 inner join
      |(SELECT get_json_object(nobench_json, '$.str1') as str1 from NoBench) right1 ON (left1.str =
      |right1.str1) WHERE left1.num BETWEEN 1 AND 10000
    """.stripMargin

}
