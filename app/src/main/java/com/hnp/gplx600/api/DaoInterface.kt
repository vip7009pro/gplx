package com.hnp.gplx600.api

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoInterface {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addQuestion(question: ErpInterface.Question)

  @Query("SELECT * FROM question_table ORDER BY `index` ASC")
  fun loadQuestion():LiveData<List<ErpInterface.Question>>


}