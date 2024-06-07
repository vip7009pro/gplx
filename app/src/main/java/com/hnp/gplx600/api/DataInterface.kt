package com.hnp.gplx600.api

import androidx.compose.runtime.Composable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

class ErpInterface
{
    data class OptionScreenData(
        var id: Int,
        var title: String,
        var icon: @Composable () -> Unit
    )
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
        var PHONE_NUMBER: String,
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

    data class SubMenuData (
        var route: String,
        var title: String,
        var icon: @Composable () -> Unit,
        var onClick: ()->Unit
    )
    data class MenuData (
        var route: String,
        var title: String,
        var icon: @Composable () -> Unit,
        var subMenu: List<SubMenuData>?
    )
    @Entity(tableName = "question_table")
    data class Question(
        @PrimaryKey(autoGenerate = false)
        val index: Int,
        val image: String,
        val no: Int,
        val text: String,
        val tip: String,
        val answers: String,
        val required: Int,
        val topic: Int,
        val a1: Int,
        val a2: Int,
        val a3: Int,
        val a4: Int,
        val b1: Int,
        val currentAnswer: Int = -1
    )
    @Entity(tableName = "test_table")
    data class Exam(
        @PrimaryKey(autoGenerate = true)
        val examIndex: Int,
        val license: String,
        val examNo: Int,
        val index: Int,
        val examAnswer: Int = -1
    )
    data class  ExamWithQuestion(
        @Embedded val exam: Exam,
        @Relation(
            parentColumn = "index",
            entityColumn = "index"
        )
        val questions: Question?
    )

}


