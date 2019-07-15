package com.fox.examples.schema

/**
  * @author zyp
  */
object MisonSQL {
  val sql1 = """select
               |    parm_t_code,
               |    get_json_object_mison(json_data,'$.dataContentQualifierDesc') AS dataContentQualifierDesc,
               |    get_json_object_mison(json_data,'$.dataContentDepartmentId') AS  dataContentDepartmentId,
               |    get_json_object_mison(json_data,'$.dataContentDomainId') AS  dataContentDomainId,
               |    get_json_object_mison(json_data,'$.gmtModify') AS  gmtModify,
               |    get_json_object_mison(json_data,'$.ownerName') AS  ownerName,
               |    get_json_object_mison(json_data,'$.ModifierName') AS  ModifierName,
               |    get_json_object_mison(json_data,'$.dataContentQualifierEnName') AS  dataContentQualifierEnName,
               |    get_json_object_mison(json_data,'$.dataContentQualifierId') AS  dataContentQualifierId,
               |    get_json_object_mison(json_data,'$.gmtCreate') AS  gmtCreate,
               |    get_json_object_mison(json_data,'$.dataContentCompositeQualifierIds') AS  dataContentCompositeQualifierIds,
               |    get_json_object_mison(json_data,'$.dataContentQualifierCnName') AS  dataContentQualifierCnName,
               |     crtor,
               |     last_moder,
               |     gmt_create,
               |     gmt_modified,
               |     json_data
               |     from ods_parm_d
               |	where parm_t_code != '120001235'""".stripMargin

  val sql2 = """select
               |	'20190104' as thedate
               |	,parm_t_code
               |	,get_json_object_mison(json_data,'$.dataContentDimAttributeEnName') as dataContentDimAttributeEnName
               |	,get_json_object_mison(json_data,'$.dataContentDepartmentId') as dataContentDepartmentId
               |	,get_json_object_mison(json_data,'$.dataContentDimAttributeCnName') as dataContentDimAttributeCnName
               |	,get_json_object_mison(json_data,'$.dataContentDimId') as dataContentDimId
               |	,get_json_object_mison(json_data,'$.dataContentDimAttributeId') as dataContentDimAttributeId
               |	,get_json_object_mison(json_data,'$.dataClassification') as dataClassification
               |	,get_json_object_mison(json_data,'$.dataContentDimAttributeHierachyId') as dataContentDimAttributeHierachyId
               |	,get_json_object_mison(json_data,'$.dataContentDimAttributeDesc') as dataContentDimAttributeDesc
               |	,get_json_object_mison(json_data,'$.dataContentParentDimAttributeId') as dataContentParentDimAttributeId
               |	,get_json_object_mison(json_data,'$.dataContentDataType') as dataContentDataType
               |	,crtor
               |	,last_moder
               |	,gmt_create
               |	,gmt_modified
               |	,json_data
               |	  from ods_parm_d2
               |	where parm_t_code != '120001242'""".stripMargin

  val sql3 = """select aa.poiid,
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
               |	                , get_json_object_mison(json_str,'$.update_flag') as update_flag
               |	                , get_json_object_mison(json_str,'$.merged_status') as merged_status
               |	                , get_json_object_mison(json_str,'$.baseinfo.name') as name
               |	                , get_json_object_mison(json_str,'$.baseinfo.address') as address
               |	                ,get_json_object_mison(json_str, '$.baseinfo.from_field.name')  as name_source
               |	                ,get_json_object_mison(json_str, '$.baseinfo.from_field.address') as address_source
               |	                ,get_json_object_mison(json_str, '$.baseinfo.from_field.navi') as navi_source
               |	                ,case
               |	                        when get_json_object_mison(json_str,'$.update_flag') != 'd' or get_json_object_mison(json_str,'$.merged_status') != '1' then get_json_object_mison(json_str, '$.baseinfo.from.src_type')
               |	                        else get_json_object_mison(json_str, '$.baseinfo.from_field.opt_type')
               |	                 end as update_flag_source
               |	        FROM s_gd_poi_base
               |
               |	) aa""".stripMargin


  val sql4 = """
               |	        select
               |	        get_json_object_mison(b.data, '$.result.poi_status.verify') as result_tag
               |	        from
               |	        s_generic_task_edit_result_json b
               |	   """.stripMargin

  /**
    * sql 5
    */

  val sql5 =
    """
      |select  coalesce(get_json_object_mison(content, '$.params.accountId'), '') as account_id
      |,coalesce(get_json_object_mison(content, '$.envInfo.devInfo.deviceId'), '') as dev_id
      |,coalesce(get_json_object_mison(content, '$.envInfo.devInfo.brand'), '') as brand
      |,coalesce(get_json_object_mison(content, '$.envInfo.devInfo.model'), '') as model
      |,coalesce(cast(get_json_object_mison(content, '$.ts') as bigint), 0) as ts
      |,coalesce(get_json_object_mison(content, '$.envInfo.devInfo.ip'), '') as ip
      |,coalesce(get_json_object_mison(content, '$.envInfo.devInfo.os'), 'ios') as os_id
      |,coalesce(get_json_object_mison(content, '$.appId'), '0') as appid
      |,'' as sdk_ch_id
      |,'APPSTORE' as ch_id
      |,1001 as biz_id
      |,coalesce(get_json_object_mison(content, '$.event'), '') as event
      |,coalesce(get_json_object_mison(content, '$.params'), '') as params
      |,coalesce(get_json_object_mison(content, '$.envInfo'), '') as envinfo
      |,coalesce(get_json_object_mison(content, '$.src'), '') as src
      |from
      |dsp_cp_sdk_events
    """.stripMargin

  val sql6 =
    """
      |SELECT loglevel AS notify_status,
      |get_json_object_mison(jsondata,"$.ap.ap_mac") AS ap_mac,
      |get_json_object_mison(jsondata,"$.ap.sw_version") AS version,
      |get_json_object_mison(jsondata,"$.ap.serial") AS serial,
      |get_json_object_mison(jsondata,"$.ap.status") AS status_code,
      |get_json_object_mison(jsondata,"$.ap.ap_model") AS ap_model,
      |get_json_object_mison(jsondata,"$.ap.sta_cnt") AS sta_cnt,
      |get_json_object_mison(jsondata,"$.ap.cpu_usage") AS cpu,
      |get_json_object_mison(jsondata,"$.ap.memory_usage") AS memory,
      |get_json_object_mison(jsondata,"$.ap.wan_recv_speed") AS rx_speed,
      |get_json_object_mison(jsondata,"$.ap.wan_sent_speed") AS tx_speed,
      |get_json_object_mison(jsondata,"$.ap.sw_version_verbose") AS sw_verbose,
      |get_json_object_mison(jsondata,"$.ap.last_reboot_reason") AS last_reboot_reason,
      |get_json_object_mison(jsondata,"$.ap.effect_md5") AS effect_md5,
      |get_json_object_mison(jsondata,"$.ap.ota_uuid") AS ota_uuid,
      |get_json_object_mison(jsondata,"$.ap.sync") AS sync_status,
      |get_json_object_mison(jsondata,"$.ap.radio[0].radio_index") AS radio_a,
      |get_json_object_mison(jsondata,"$.ap.radio[0].channel") AS channel_a,
      |get_json_object_mison(jsondata,"$.ap.radio[0].channel_usage") AS utilities_a,
      |get_json_object_mison(jsondata,"$.ap.radio[0].noise") AS noise_a,
      |get_json_object_mison(jsondata,"$.ap.radio[1].radio_index") AS radio_b,
      |get_json_object_mison(jsondata,"$.ap.radio[1].channel") AS channel_b,
      |get_json_object_mison(jsondata,"$.ap.radio[1].channel_usage") AS utilities_b,
      |get_json_object_mison(jsondata,"$.ap.radio[1].noise") AS noise_b,
      |get_json_object_mison(jsondata,"$.ap.link_speed") AS link_speed,
      |get_json_object_mison(jsondata,"$.ap.public_ip") AS public_ip,
      |get_json_object_mison(jsondata,"$.ap.wan_type") AS wan_type,
      |get_json_object_mison(jsondata,"$.ap.wan_recv") AS wan_recv,
      |get_json_object_mison(jsondata,"$.ap.wan_sent") AS wan_sent,
      |get_json_object_mison(jsondata,"$.wan_ip") AS wan_ip
      |FROM wifi_vac_log_internal
    """.stripMargin

  val sql7 =
    """
      |select seedurl as ip
      |, get_json_object_mison(description, '$.weakUsername') as u
      |, get_json_object_mison(description, '$.weakPassword') as p
      |from
      |odl_cactus_scalog_di
    """.stripMargin


  val sql8 =
    """
      |select ts,host_name,full_parent_name,item_id,item_name,
      |get_json_object_mison(value,'$.cpu') cpu_avg,
      |get_json_object_mison(value,'$.mem')mem_avg,
      |get_json_object_mison(value,'$.load1')load1_avg,
      |get_json_object_mison(value,'$.nginx_qps') nginx_qps_sum,
      |get_json_object_mison(value,'$.nginx_rt') nginx_rt_avg
      |from odps_gongxiang_monitor_data
    """.stripMargin

  val sql9 =
    """
      |select feed_id,
      |'' as is_jingxuan,
      |'Y' is_zhuanti
      |from toutiao_all_feeds
      |union all
      |select feed_id,
      |if(get_json_object_mison(feature,'$.isChoiceness')  = 'true', 'Y', 'N') as is_jingxuan,
      |'' as is_zhuanti
      |from toutiao_all_feeds
    """.stripMargin

  val sql10 =
    """
      |SELECT  pvid
      |,get_json_object_mison(user_json, '$.user_id') AS user_id
      |,timestamp
      |,get_json_object_mison(user_json, '$.g_cart') AS user__g_cart
      |,get_json_object_mison(user_json, '$.g_fav') AS user__g_fav
      |,get_json_object_mison(user_json, '$.g_pay') AS user__g_pay
      |,get_json_object_mison(user_json, '$.st') AS user__st
      |,get_json_object_mison(user_json, '$.time') AS user__time
      |,get_json_object_mison(user_json, '$.opt_seq_length') AS user__seq_length
      |,get_json_object_mison(user_json, '$.user_behavior_seq') AS user__user_behavior_seq
      |FROM    gul_tpp_tt_feature_log_mm
    """.stripMargin
}
