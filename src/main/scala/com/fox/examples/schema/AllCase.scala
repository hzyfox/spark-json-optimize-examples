package com.fox.examples.schema

/**
  * @author zyp
  */
class AllCase {

}


case class dws_rsm_dm_credit_huabei_mysql2odps_dd(user_id: String,
                                                  phone: String,
                                                  gmt_create: String,
                                                  gmt_modified: String,
                                                  topten_person: String,
                                                  start_nickname: String,
                                                  end_nickname: String,
                                                  input: String,
                                                  result: String)

case class ods_pcic_dmc_model_data(tnt_inst_id: String,
                                   id: String,
                                   gmt_create: String,
                                   gmt_creator: String,
                                   gmt_modified: String,
                                   gmt_modifier: String,
                                   category: String,
                                   entity_type: String,
                                   entity_name: String,
                                   entity_code: String,
                                   data_provider: String,
                                   data_time: String,
                                   object_content: String,
                                   metadata: String,
                                   data_org_id: String,
                                   collect_execution_id: String) //1

case class ods_pdm_order_operate(operate_no: String,
                                 data_col: String) //2.0
case class ods_lnia_org_info(inst_code: String,
                             ip_id: String,
                             ip_role_id: String,
                             in_acct_no: String,
                             in_acct_tp: String,
                             out_acct_no: String,
                             out_acct_tp: String) //2.1
case class ods_parm_d(
                       parm_t_code: String,
                       crtor: String,
                       last_moder: String,
                       gmt_create: String,
                       gmt_modified: String,
                       json_data: String) //3

case class ods_parm_d2(
                        parm_t_code: String,
                        crtor: String,
                        last_moder: String,
                        gmt_create: String,
                        gmt_modified: String,
                        json_data: String) //4

case class s_gd_poi_base(poiid: String,
                         json_str: String) //5
case class cms_ces_generic_review_df(id: BigInt,
                                     resource_id: String,
                                     task_type: String
                                    ) //6.0


case class s_generic_task_edit_result_json(data: String) //6.1


/**
  * 7
  * select  coalesce(get_json_object(content, '$.params.accountId'), '') as account_id
  * ,coalesce(get_json_object(content, '$.envInfo.devInfo.deviceId'), '') as dev_id
  * ,coalesce(get_json_object(content, '$.envInfo.devInfo.brand'), '') as brand
  * ,coalesce(get_json_object(content, '$.envInfo.devInfo.model'), '') as model
  * ,coalesce(cast(get_json_object(content, '$.ts') as bigint), 0) as ts
  * ,coalesce(get_json_object(content, '$.envInfo.devInfo.ip'), '') as ip
  * ,coalesce(get_json_object(content, '$.envInfo.devInfo.os'), 'ios') as os_id
  * ,coalesce(get_json_object(content, '$.appId'), '0') as appid
  * ,'' as sdk_ch_id
  * ,'APPSTORE' as ch_id
  * ,1001 as biz_id
  * ,coalesce(get_json_object(content, '$.event'), '') as event
  * ,coalesce(get_json_object(content, '$.params'), '') as params
  * ,coalesce(get_json_object(content, '$.envInfo'), '') as envinfo
  * ,coalesce(get_json_object(content, '$.src'), '') as src
  * from
  *
  * dsp_cp_sdk_events
  *
  *
  * seraaaenaae=saara-uaaa111_11111_a1_t11`aast=saara-uaaa111`aaaaa=e1111`at=1111111111111`{"ts":1111111111111,"aaaaa":"111111","eaent":"saa.user.anlane","aaraas":{"aaaauntaa":"a11fe11111a1a1a1111a1111e1a1a111"},"envInfo":{"aeaanfa":{"uuaa":"11aa1111-111E-1111-11a1-1111aa1a1a11","arana":"aaale","net":"aafa","aeaaaeaa":"1111111a-Ea11-1aaa-1a11-1111aa1aEa11","fr":"aaa 11.1.1","as":"aas","aaf":"aaale","lanaatuae":"1.111111","raaaaae":"1111","latatuae":"1.111111","tatalaaae":"11111","aaeratar":"你你你你","lana":"aa-aans-aa","taaeaane":"aaa+11:11","aaael":"aaaane 1s","aauntra":"aa","res":"1111*111","aa":"111.111.111.111"},"runaa":"11aa1111-111E-1111-11a1-1111aa1a1a11"},"sra":"aarussaa"}
  *
  * @param content
  **/

case class dsp_cp_sdk_events(content: String)


/**
  * SELECT
  * t1.loglevel AS notify_status,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.ap_mac") AS ap_mac,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.sw_version") AS version,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.serial") AS serial,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.status") AS status_code,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.ap_model") AS ap_model,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.sta_cnt") AS sta_cnt,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.cpu_usage") AS cpu,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.memory_usage") AS memory,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.wan_recv_speed") AS rx_speed,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.wan_sent_speed") AS tx_speed,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.sw_version_verbose") AS sw_verbose,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.last_reboot_reason") AS last_reboot_reason,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.effect_md5") AS effect_md5,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.ota_uuid") AS ota_uuid,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.sync") AS sync_status,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.radio[0].radio_index") AS radio_a,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.radio[0].channel") AS channel_a,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.radio[0].channel_usage") AS utilities_a,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.radio[0].noise") AS noise_a,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.radio[1].radio_index") AS radio_b,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.radio[1].channel") AS channel_b,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.radio[1].channel_usage") AS utilities_b,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.radio[1].noise") AS noise_b,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.link_speed") AS link_speed,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.public_ip") AS public_ip,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.wan_type") AS wan_type,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.wan_recv") AS wan_recv,
  * GET_JSON_OBJECT(t1.jsondata,"$.ap.wan_sent") AS wan_sent,
  * GET_JSON_OBJECT(t1.jsondata,"$.wan_ip") AS wan_ip,
  * FROM (
  * SELECT apipath,jsondata,loglevel
  * FROM wifi_vac_log_internal
  * ) t1
  */

case class wifi_vac_log_internal(apipath: String, loglevel: String, jsondata: String)

/**
  * select
  * seedurl as ip
  * , get_json_object(description, '$.weakUsername') as u
  * , get_json_object(description, '$.weakPassword') as p
  * from
  * odl_cactus_scalog_di
  *
  */
case class odl_cactus_scalog_di(seedurl: String, occurtime: String, vultype: String, description: String)

/**
  * select
  * ts,host_name,node_name,full_parent_name,item_id,item_name,
  * avg(get_json_object(value,'$.cpu')) cpu_avg,
  * avg(get_json_object(value,'$.mem')) mem_avg,
  * avg(get_json_object(value,'$.load1')) load1_avg,
  * sum(get_json_object(value,'$.nginx_qps')) nginx_qps_sum,
  * avg(get_json_object(value,'$.nginx_rt')) nginx_rt_avg
  * from odps_gongxiang_monitor_data
  * group by ts, host_name,node_name,full_parent_name,item_id,item_name
  *
  */

case class odps_gongxiang_monitor_data(ts: String, host_name: String, full_parent_name: String,
                                       item_id: Long, item_name: String, value: String)


/**
  * select a.feed_id, c.is_jingxuan, c.is_zhuanti
  * from
  * (
  * select feed_id, name
  * from
  * shoutao_toutiao_feed_online_7days
  * ) a
  * join
  * (
  * select feed_id,
  * '' as is_jingxuan,
  * 'Y' is_zhuanti
  * from toutiao_all_feeds
  * union all
  * select feed_id,
  * if(get_json_object(feature,'$.isChoiceness')  = 'true', 'Y', 'N') as is_jingxuan,
  * '' as is_zhuanti
  * from toutiao_all_feeds
  * ) c
  * on a.feed_id = c.feed_id
  *
  */

case class toutiao_all_feeds(feed_id: String, form: String, show_date: String, feature: String)

case class shoutao_toutiao_feed_online_7days(feed_id: String, name: String)

/**
  * SELECT  pvid
  * ,GET_JSON_OBJECT(user_json, '$.user_id') AS user_id
  * ,timestamp
  * ,GET_JSON_OBJECT(user_json, '$.g_cart') AS user__g_cart
  * ,GET_JSON_OBJECT(user_json, '$.g_fav') AS user__g_fav
  * ,GET_JSON_OBJECT(user_json, '$.g_pay') AS user__g_pay
  * ,GET_JSON_OBJECT(user_json, '$.st') AS user__st
  * ,GET_JSON_OBJECT(user_json, '$.time') AS user__time
  * ,GET_JSON_OBJECT(user_json, '$.opt_seq_length') AS user__seq_length
  * ,GET_JSON_OBJECT(user_json, '$.user_behavior_seq') AS user__user_behavior_seq
  * FROM    gul_tpp_tt_feature_log_mm
  **/
case class gul_tpp_tt_feature_log_mm(pvid: String, timestamp: String, user_json : String)