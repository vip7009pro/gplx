package com.hnp.gplx600.api

import androidx.lifecycle.LiveData
class AppRepositories(private val questionDao: DaoInterface) {
  val loadAllQuestion: LiveData<List<ErpInterface.Question>> = questionDao.loadQuestion()
  suspend fun addQuestion(question: ErpInterface.Question) {
    questionDao.addQuestion(question)
  }
}