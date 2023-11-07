package com.cmsbando.erp.api

class ErpInterface
{
    data class LoginInfo(
        var command: String,
        var user: String,
        var pass: String
    )

    data class Employee(
        var EMPL_IMAGE: String,
        var ADD_COMMUNE: String,
        var ADD_DISTRICT: String,
        var ADD_PROVINCE: String,
        var ADD_VILLAGE: String,
        var ATT_GROUP_CODE: Int,
        var CMS_ID: String,
        var CTR_CD: String,
        var DOB: String,
        var EMAIL: String,
        var EMPL_NO: String,
        var FACTORY_CODE: Int,
        var FACTORY_NAME: String,
        var FACTORY_NAME_KR: String,
        var FIRST_NAME: String,
        var HOMETOWN: String,
        var JOB_CODE: Int,
        var JOB_NAME: String,
        var JOB_NAME_KR: String,
        var MAINDEPTCODE: Int,
        var MAINDEPTNAME: String,
        var MAINDEPTNAME_KR: String,
        var MIDLAST_NAME: String,
        var ONLINE_DATETIME: String,
        var PASSWORD: String,
        var PHONE_Int: String,
        var POSITION_CODE: Int,
        var POSITION_NAME: String,
        var POSITION_NAME_KR: String,
        var REMARK: String,
        var SEX_CODE: Int,
        var SEX_NAME: String,
        var SEX_NAME_KR: String,
        var SUBDEPTCODE: Int,
        var SUBDEPTNAME: String,
        var SUBDEPTNAME_KR: String,
        var WORK_POSITION_CODE: Int,
        var WORK_POSITION_NAME: String,
        var WORK_POSITION_NAME_KR: String,
        var WORK_SHIFT_CODE: Int,
        var WORK_SHIF_NAME: String,
        var WORK_SHIF_NAME_KR: String,
        var WORK_START_DATE: String,
        var WORK_STATUS_CODE: Int,
        var WORK_STATUS_NAME: String,
        var WORK_STATUS_NAME_KR: String,
    )

}
