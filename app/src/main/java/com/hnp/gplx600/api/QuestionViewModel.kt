package com.hnp.gplx600.api

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application): AndroidViewModel(application) {
  private val loadAllQuestion: List<ErpInterface.Question>
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
  }


}