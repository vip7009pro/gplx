package com.hnp.gplx600.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hnp.gplx600.api.ErpInterface
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoInterface {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addQuestion(question: ErpInterface.Question)
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
}