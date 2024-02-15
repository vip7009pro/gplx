package com.hnp.gplx600.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GlobalVariable: ViewModel() {
  var currentServer: String by mutableStateOf("MAIN_SERVER")
  var globalDialogState: Boolean by mutableStateOf(false)
  var globalDialogTitle: String by mutableStateOf("")
  var globalDialogText: String by mutableStateOf("")
  var globalDialogCat: String by mutableStateOf("success")
  var onDialogConfirm: (() -> Unit)? = null
  var onDialogCancel: (() -> Unit)? = null
  var token: String by mutableStateOf("reset")

  var userData: ErpInterface.Employee by mutableStateOf(ErpInterface.Employee(
    ADD_COMMUNE= "Đông Xuân",
    ADD_DISTRICT= "Sóc Sơn",
    ADD_PROVINCE= "Hà Nội",
    ADD_VILLAGE= "Thôn Phú Thọ",
    ATT_GROUP_CODE= 1,
    CMS_ID= "CMS1179",
    CTR_CD= "002",
    DOB= "1993-10-18T00=00=00.000Z",
    EMAIL= "nvh1903@cmsbando.com",
    EMPL_NO= "NHU1903",
    FACTORY_CODE= 1,
    FACTORY_NAME= "Nhà máy 1",
    FACTORY_NAME_KR= "1공장",
    FIRST_NAME= "HÙNG3",
    HOMETOWN= "Phụ Thọ - Đông Xuân - Sóc Sơn - Hà Nội",
    JOB_CODE= 1,
    JOB_NAME= "Dept Staff",
    JOB_NAME_KR= "부서담당자",
    MAINDEPTCODE= 1,
    MAINDEPTNAME= "QC",
    MAINDEPTNAME_KR= "품질",
    MIDLAST_NAME= "NGUYỄN VĂN",
    ONLINE_DATETIME= "2022-07-12T20=49=52.600Z",
    PASSWORD= "",
    PHONE_NUMBER= "0971092454",
    POSITION_CODE= 3,
    POSITION_NAME= "Staff",
    POSITION_NAME_KR= "사원",
    REMARK= "",
    SEX_CODE= 1,
    SEX_NAME= "Nam",
    SEX_NAME_KR= "남자",
    SUBDEPTCODE= 2,
    SUBDEPTNAME= "PD",
    SUBDEPTNAME_KR= "통역",
    WORK_POSITION_CODE= 2,
    WORK_POSITION_NAME= "PD",
    WORK_POSITION_NAME_KR= "PD",
    WORK_SHIFT_CODE= 0,
    WORK_SHIF_NAME= "Hành Chính",
    WORK_SHIF_NAME_KR= "정규",
    WORK_START_DATE= "2019-03-11T00=00=00.000Z",
    WORK_STATUS_CODE= 1,
    WORK_STATUS_NAME= "Đang làm",
    WORK_STATUS_NAME_KR= "근무중",
    EMPL_IMAGE= "N",
  ))
  fun getServer(): String {
    var sv: String = ""
    when(currentServer) {
      "MAIN_SERVER"-> sv = "http:14.160.33.94:5013"
      "SUB_SERVER"-> sv = "http:14.160.33.94:3007"
    }
    return sv
  }
  fun showDialog(
    dialogCat: String = "success",
    dialogTitle: String = "Title",
    dialogText: String = "Text",
    dlConfirm: (() -> Unit),
    dlCancel: (() -> Unit)
  ) {
    globalDialogState = true
    globalDialogCat = dialogCat
    globalDialogTitle = dialogTitle
    globalDialogText = dialogText
    onDialogConfirm = dlConfirm
    onDialogCancel = dlCancel
  }
}