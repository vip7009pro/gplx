package com.hnp.gplx600.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hnp.gplx600.api.ErpInterface
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoInterface {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addQuestion(question: ErpInterface.Question)
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addExam(exam: ErpInterface.Exam)
  //load exam of specific license
  @Transaction
  @Query("SELECT * FROM test_table WHERE license=:license ORDER BY examNo ASC")
  fun getExamWithQuestionByLicense(license: String): Flow<List<ErpInterface.ExamWithQuestion>>
  //get only exam without question of a specific license
  @Query("SELECT * FROM test_table WHERE license=:license")
  fun getExamByLicense(license: String): Flow<List<ErpInterface.Exam>>
  @Query("SELECT * FROM question_table")
  fun loadQuestion():List<ErpInterface.Question>
  @Query("SELECT * FROM question_table")
  fun getAllQuestion(): Flow<List<ErpInterface.Question>>
  @Query("SELECT * FROM question_table WHERE `index`=:index")
  fun loadQuestionByIndex(index:Int): ErpInterface.Question
  @Query("DELETE FROM question_table WHERE `index`=:index")
  fun deleteByIndex(index: Int)
  @Query("DELETE FROM question_table")
  suspend fun deleteAllQuestion()
  @Update
  fun updateQuestion(question: ErpInterface.Question)
  @Delete
  fun deleteQuestion(question: ErpInterface.Question)
  @Query("UPDATE question_table SET currentAnswer = :answer WHERE `index`=:index")
  suspend  fun updateAnswerByIndex(index: Int, answer: Int)
  @Query("UPDATE test_table SET examAnswer = :answer WHERE `index`=:examIndex")
  suspend  fun updateAnswerByExamIndex(examIndex: Int, answer: Int)
  @Query("UPDATE question_table SET currentAnswer = -1")
  suspend fun resetAnswer()
  @Query("SELECT * FROM test_table WHERE examIndex = :examIndex ORDER BY examNo ASC")
  fun getExamWithQuestion(examIndex: Int): Flow<List<ErpInterface.ExamWithQuestion>>
  //delete all exams
  @Query("DELETE FROM test_table")
  suspend fun deleteAllExam()
//get lastest exam no of a license from test_table
  @Query("SELECT MAX(examNo) AS maxExamNo FROM test_table WHERE license = :license")
  fun getLastestExamNo(license: String): Flow<List<ErpInterface.MaxExam>>
  //select exam by license and exam no from test_table join with question_table to get question contents
  @Transaction
  @Query("SELECT test_table.examIndex AS examIndex, test_table.license, test_table.examNo, test_table.`index`, test_table.examAnswer, question_table.image,  question_table.text, question_table.tip, question_table.answers, question_table.required, question_table.topic, test_table.questionNo  FROM test_table LEFT JOIN question_Table ON test_table.`index` = question_table.`index` WHERE test_table.license = :license AND test_table.examNo = :examNo order by test_table.questionNo asc")
  fun getExamWithQuestionByLicenseAndExamNo(license: String, examNo: Int): Flow<List<ErpInterface.ExamQuestionByLicenseAndExamNo>>

}