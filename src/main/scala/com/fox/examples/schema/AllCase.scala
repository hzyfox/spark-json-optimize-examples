package com.fox.examples.schema

/**
  * @author zyp
  */
class AllCase {

}


case class dws_rsm_dm_credit_huabei_mysql2odps_dd(user_id:String,
                                                  phone:String,
                                                  gmt_create:String,
                                                  gmt_modified:String,
                                                  topten_person:String,
                                                  start_nickname:String,
                                                  end_nickname:String,
                                                  input:String,
                                                  result:String)

case class ods_pcic_dmc_model_data(tnt_inst_id:String,
                                   id:String,
                                   gmt_create:String,
                                   gmt_creator:String,
                                   gmt_modified:String,
                                   gmt_modifier:String,
                                   category:String,
                                   entity_type:String,
                                   entity_name:String,
                                   entity_code:String,
                                   data_provider:String,
                                   data_time:String,
                                   object_content:String,
                                   metadata:String,
                                   data_org_id:String,
                                   collect_execution_id:String) //1

case class ods_pdm_order_operate(operate_no:String,
                                 data_col:String)  //2.0
case class ods_lnia_org_info(inst_code:String,
                             ip_id:String,
                             ip_role_id:String,
                             in_acct_no:String,
                             in_acct_tp:String,
                             out_acct_no:String,
                             out_acct_tp:String)  //2.1
case class ods_parm_d(
                      parm_t_code:String,
                      crtor:String,
                      last_moder:String,
                      gmt_create:String,
                      gmt_modified:String,
                      json_data:String)   //3

case class ods_parm_d2(
                       parm_t_code:String,
                       crtor:String,
                       last_moder:String,
                       gmt_create:String,
                       gmt_modified:String,
                       json_data:String)   //4

case class s_gd_poi_base(poiid:String,
                         json_str:String) //5
case class cms_ces_generic_review_df(id:BigInt,
                                     resource_id:String,
                                     task_type:String
                                     ) //6.0



case class s_generic_task_edit_result_json(data:String) //6.1


/**
  * 7
  *  select  coalesce(get_json_object(content, '$.params.accountId'), '') as account_id
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
  * from    (
  * select  split_part(content, '`', 5) as content
  * from    aligames_dc.dsp_cp_sdk_events
  * where   dt = '20190101'
  * and     hour = '11'
  * ) t1
  *
  * seraaaenaae=saara-uaaa111_11111_a1_t11`aast=saara-uaaa111`aaaaa=e1111`at=1111111111111`{"ts":1111111111111,"aaaaa":"111111","eaent":"saa.user.anlane","aaraas":{"aaaauntaa":"a11fe11111a1a1a1111a1111e1a1a111"},"envInfo":{"aeaanfa":{"uuaa":"11aa1111-111E-1111-11a1-1111aa1a1a11","arana":"aaale","net":"aafa","aeaaaeaa":"1111111a-Ea11-1aaa-1a11-1111aa1aEa11","fr":"aaa 11.1.1","as":"aas","aaf":"aaale","lanaatuae":"1.111111","raaaaae":"1111","latatuae":"1.111111","tatalaaae":"11111","aaeratar":"你你你你","lana":"aa-aans-aa","taaeaane":"aaa+11:11","aaael":"aaaane 1s","aauntra":"aa","res":"1111*111","aa":"111.111.111.111"},"runaa":"11aa1111-111E-1111-11a1-1111aa1a1a11"},"sra":"aarussaa"}
  *
  * @param content
  */

case class dsp_cp_sdk_events(content: String)






/**
  *
  */




