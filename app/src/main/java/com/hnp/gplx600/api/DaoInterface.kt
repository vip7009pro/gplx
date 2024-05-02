package com.hnp.gplx600.api

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoInterface {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addQuestion(question: ErpInterface.Question)
  @Query("SELECT * FROM question_table ORDER BY `index` ASC")
  fun loadQuestion(index: Int):List<ErpInterface.Question>
  @Query("SELECT * FROM question_table")
  fun getAllQuestion():List<ErpInterface.Question>
  @Query("SELECT * FROM question_table WHERE `index`=:index")
  fun loadQuestionByIndex(index:Int):ErpInterface.Question
  @Query("DELETE FROM question_table WHERE `index`=:index")
  fun deleteByIndex(index: Int)
  @Query("DELETE FROM question_table")
  fun deleteAllQuestion()
  @Update
  fun updateQuestion(question: ErpInterface.Question)
  @Delete
  fun deleteQuestion(question: ErpInterface.Question)
}