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
  fun addQuestion(question: ErpInterface.Question) {
    viewModelScope.launch {
      dao.addQuestion(question)
    }
  }

}
/*
class QuestionViewModel(application: Application): AndroidViewModel(application) {
  */
/*private val loadAllQuestion: List<ErpInterface.Question>
  private val repositories: AppRepositories
  init {
    val questionDao = AppDataBase.getDatabase(application).questionDao()
    repositories = AppRepositories(questionDao)
    loadAllQuestion = repositories.loadAllQuestion
  }
  fun addQuestion(question: ErpInterface.Question){
    viewModelScope.launch(Dispatchers.IO) {
      repositories.addQuestion(question)
    }
  }
  fun updateQuestion(question: ErpInterface.Question){
    viewModelScope.launch(Dispatchers.IO) {
      repositories.updateQuestion(question)
    }
  }
  fun deleteQuestion(question: ErpInterface.Question){
    viewModelScope.launch(Dispatchers.IO) {
      repositories.deleteQuestion(question)
    }
  }
  fun deleteAllQuestion(){
    viewModelScope.launch(Dispatchers.IO) {
      repositories.deleteAllQuestion()
    }
  }
  fun loadAllQuestion(): List<ErpInterface.Question>{
    return loadAllQuestion
  }*//*



}*/
