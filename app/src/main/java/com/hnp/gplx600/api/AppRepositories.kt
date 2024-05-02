package com.hnp.gplx600.api


class AppRepositories(private val questionDao: DaoInterface) {
  val loadAllQuestion: List<ErpInterface.Question> = questionDao.getAllQuestion()
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