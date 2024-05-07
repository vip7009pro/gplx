package com.hnp.gplx600.roomdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hnp.gplx600.api.ErpInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class QuestionViewModel(private val dao: DaoInterface): ViewModel() {
  fun getAllQuestion(): Flow<List<ErpInterface.Question>> {
    return dao.getAllQuestion()
  }
  fun getAllA1Question(): Flow<List<ErpInterface.Question>> {
    return dao.getAllA1Question()
  }
  fun getAllA2Question(): Flow<List<ErpInterface.Question>> {
    return dao.getAllA2Question()
  }
  fun getAllA3Question(): Flow<List<ErpInterface.Question>> {
    return dao.getAllA3Question()
  }
  fun getAllA4Question(): Flow<List<ErpInterface.Question>> {
    return dao.getAllA4Question()
  }
  fun getAllB1Question(): Flow<List<ErpInterface.Question>> {
    return dao.getAllB1Question()
  }

  fun addQuestion(question: ErpInterface.Question) {
    viewModelScope.launch {
      dao.addQuestion(question)
    }
  }
  fun deleteAllQuestion() {
    viewModelScope.launch {
      dao.deleteAllQuestion()
    }

  }
}