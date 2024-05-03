package com.hnp.gplx600.roomdb

import com.hnp.gplx600.api.ErpInterface
import kotlinx.coroutines.flow.Flow

class AppRepositories(private val questionDao: DaoInterface) {
  val loadAllQuestion: Flow<List<ErpInterface.Question>> = questionDao.getAllQuestion()
  suspend fun addQuestion(question: ErpInterface.Question) {
    questionDao.addQuestion(question)
  }
  suspend fun updateQuestion(question: ErpInterface.Question) {
    questionDao.updateQuestion(question)
  }
  suspend fun deleteQuestion(question: ErpInterface.Question) {
    questionDao.deleteQuestion(question)
  }
  suspend fun deleteAllQuestion() {
    questionDao.deleteAllQuestion()
  }


}