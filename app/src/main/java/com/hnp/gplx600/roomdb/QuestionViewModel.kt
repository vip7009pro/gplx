package com.hnp.gplx600.roomdb

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hnp.gplx600.api.ErpInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class QuestionViewModel(private val dao: DaoInterface): ViewModel() {

  fun getAllQuestion(): Flow<List<ErpInterface.Question>> {
    return dao.getAllQuestion()
  }
  fun getAllQuestion1(): List<ErpInterface.Question> {
      return dao.getAllQuestion1()
    }

  fun addQuestion(question: ErpInterface.Question) {
    viewModelScope.launch {
      dao.addQuestion(question)
    }
  }
  fun addExam(exam: ErpInterface.Exam) {
    viewModelScope.launch {
      dao.addExam(exam)
    }
  }
  fun updateAnswer(index: Int, answer: Int) {
    viewModelScope.launch {
      dao.updateAnswerByIndex(index, answer)
    }
  }
  fun updateAnswerByExamIndex(examIndex: Int, answer: Int) {
    viewModelScope.launch {
      print(examIndex)
      dao.updateAnswerByExamIndex(examIndex, answer)
    }
  }
  fun deleteAllQuestion() {
    viewModelScope.launch {
      dao.deleteAllQuestion()
    }
  }
  fun resetAnswer() {
    viewModelScope.launch {
      dao.resetAnswer()
    }
  }
  fun getExamQuestion(examIndex:Int): Flow<List<ErpInterface.ExamWithQuestion>> {
    return dao.getExamWithQuestion(examIndex)
  }
  //get exams by license
  fun getExamsByLicense(license: String): Flow<List<ErpInterface.Exam>> {
    return dao.getExamByLicense(license)
  }
  //getExamWithQuestionByLicense
  fun getExamWithQuestionByLicense(license: String): Flow<List<ErpInterface.ExamListStatus>> {
    return dao.getExamWithQuestionByLicense(license)
  }
  fun getExamWithQuestionByLicense1(license: String): List<ErpInterface.ExamListStatus> {
    return dao.getExamWithQuestionByLicense1(license)
  }

  //delete all exams
  fun deleteAllExam() {
    viewModelScope.launch {
      dao.deleteAllExam()
    }
  }
  //delete exam by examNo and license
  fun deleteExam(examNo: Int, license: String) {
    viewModelScope.launch {
      dao.deleteExamByExamNoAndLicense(examNo, license)
    }
  }
  //get lastest exam no of a license from test_table
  fun getExamNo(license: String): Flow<List<ErpInterface.MaxExam>> {
    return dao.getLastestExamNo(license)
  }
  fun getExamNo1(license: String): List<ErpInterface.MaxExam> {
    var examNoList = mutableListOf<ErpInterface.MaxExam>()
    viewModelScope.launch {
      examNoList  = dao.getLastestExamNo1(license).toMutableList()
    }
    return examNoList
  }
  //getExamWithQuestionByLicenseAndExamNo
  fun getExamWithQuestionByLicenseAndExamNo(license: String, examNo: Int): Flow<List<ErpInterface.ExamQuestionByLicenseAndExamNo>> {
    return dao.getExamWithQuestionByLicenseAndExamNo(license, examNo)
  }
  fun getExamWithQuestionByLicenseAndExamNo1(license: String, examNo: Int): List<ErpInterface.ExamQuestionByLicenseAndExamNo> {
      return dao.getExamWithQuestionByLicenseAndExamNo1(license, examNo)
    }

  //reset exam answer
  fun resetExamAnswer(license: String, examNo: Int) {
    viewModelScope.launch {
      dao.resetExamAnswerByLicenseAndExamNo(license, examNo)
    }
  }

}