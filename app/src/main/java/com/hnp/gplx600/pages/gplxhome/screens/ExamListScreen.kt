package com.hnp.gplx600.pages.gplxhome.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.roomdb.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamListScreen(
  navController: NavController,
  db: AppDataBase,
  vm: QuestionViewModel,
  globalVar: GlobalVariable,
) {
  //get lastest exam no from database of the current license
  val examNo = vm.getExamNo(globalVar.currentLicense).collectAsState(initial = emptyList())
  val latest_exam_no = if (examNo.value.isNotEmpty()) examNo.value[0].maxExamNo else 0
  val examList = vm.getExamWithQuestionByLicense(globalVar.currentLicense)
    .collectAsState(initial = emptyList())
  val dataList = vm.getAllQuestion().collectAsState(initial = emptyList())
  var filteredList: List<ErpInterface.Question> = emptyList()
  when (globalVar.currentLicense) {
    "A1" -> {
      filteredList = dataList.value.filter {
        it.a1 != 0
      }
    }

    "A2" -> {
      filteredList = dataList.value.filter {
        it.a2 != 0
      }
    }

    "A3" -> {
      filteredList = dataList.value.filter {
        it.a3 != 0
      }
    }

    "A4" -> {
      filteredList = dataList.value.filter {
        it.a4 != 0
      }
    }

    "B1" -> {
      filteredList = dataList.value.filter {
        it.b1 != 0
      }
    }

    else -> {
      filteredList = dataList.value
    }
  }
  val requiredQuestionList = filteredList.filter {
    it.required == 1 && it.topic == 1
  }
  val examDefinition = mapOf(
    "A1" to ErpInterface.PartObject(1, 1, 6, 1, 0, 1, 1, 0, 7, 7),
    "A2" to ErpInterface.PartObject(1, 1, 6, 1, 0, 1, 1, 0, 7, 7),
    "A3" to ErpInterface.PartObject(1, 1, 6, 1, 0, 1, 1, 0, 7, 7),
    "A4" to ErpInterface.PartObject(1, 1, 6, 1, 0, 1, 1, 0, 7, 7),
    "B1" to ErpInterface.PartObject(1, 1, 6, 1, 0, 1, 1, 1, 9, 9),
    "B2" to ErpInterface.PartObject(1, 1, 7, 1, 1, 1, 2, 1, 10, 10),
    "C" to ErpInterface.PartObject(1, 1, 7, 1, 1, 1, 2, 1, 14, 11),
    "D" to ErpInterface.PartObject(1, 1, 7, 1, 1, 1, 2, 1, 16, 14),
    "E" to ErpInterface.PartObject(1, 1, 7, 1, 1, 1, 2, 1, 16, 14),
    "F" to ErpInterface.PartObject(1, 1, 7, 1, 1, 1, 2, 1, 16, 14),
  )
  val currentExamDefinition = examDefinition[globalVar.currentLicense]
  val examNoList = examList.value.distinctBy { it.examNo }.sortedBy { it.examNo }
//  print(examNoList)
//  Log.d("examNoList", "$examNoList")
  LaunchedEffect(key1 = true) {

  }
  Scaffold(topBar = {
    TopAppBar(modifier = Modifier
      .height(50.dp)
      .background(color = Color.Green), title = {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(40.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "Danh sách đề thi")
      }
    }, navigationIcon = {
      IconButton(onClick = { navController.navigateUp() }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
      }
    })
  }) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

      ) {
        Button(modifier = Modifier
          .height(40.dp)
          .fillMaxWidth(0.5f), onClick = {
          val part1 = requiredQuestionList.shuffled().take(1)
          val part2 = filteredList.filter {
            !part1.contains(it) && (it.index in 1..16)
          }.shuffled().take(currentExamDefinition!!.part2)
          val part3 = filteredList.filter {
            !part1.contains(it) && (it.index in 17..123)
          }.shuffled().take(currentExamDefinition.part3)
          val part4 = filteredList.filter {
            !part1.contains(it) && (it.index in 124..166)
          }.shuffled().take(currentExamDefinition.part4)
          val part5 = filteredList.filter {
            !part1.contains(it) && (it.index in 167..192)
          }.shuffled().take(currentExamDefinition.part5)
          val part6 = filteredList.filter {
            !part1.contains(it) && (it.index in 193..213)
          }.shuffled().take(currentExamDefinition.part6)
          val part7 = filteredList.filter {
            !part1.contains(it) && (it.index in 214..269)
          }.shuffled().take(currentExamDefinition.part7)
          val part8 = filteredList.filter {
            !part1.contains(it) && (it.index in 270..304)
          }.shuffled().take(currentExamDefinition.part8)
          val part9 = filteredList.filter {
            !part1.contains(it) && (it.index in 305..486)
          }.shuffled().take(currentExamDefinition.part9)
          val part10 = filteredList.filter {
            !part1.contains(it) && (it.index in 487..600)
          }.shuffled().take(currentExamDefinition.part10)

          val finalExamList =
            part1 + part2 + part3 + part4 + part5 + part6 + part7 + part8 + part9 + part10
          //insert every exam from final Exam list to database
          finalExamList.forEachIndexed {index, element ->
            vm.addExam(
              ErpInterface.Exam(
                examIndex = 0,
                license = globalVar.currentLicense,
                examNo = latest_exam_no + 1,
                questionNo = index + 1,
                index = element.index
              )
            )
          }

        }) {
          Text(
            text = "Tạo đề",
            modifier = Modifier
              .fillMaxSize()
              .align(Alignment.CenterVertically)
          )
        }
        Button(modifier = Modifier
          .height(40.dp)
          .fillMaxWidth(), onClick = {
          vm.deleteAllExam()
        }) {
          Text(
            text = "Xóa đề",
            modifier = Modifier
              .fillMaxSize()
              .align(Alignment.CenterVertically)
          )
        }
      }
      LazyColumn(
        modifier = Modifier.fillMaxSize()
      ) {
        items(examNoList) {
          //create card show exam name and exam id, and the number of question
          Card(modifier = Modifier.padding(5.dp), onClick = {
            globalVar.currentExamNo = it.examNo
            navController.navigate("examscreen") {
              popUpTo("examscreen") {
                inclusive = true
              }
            }
          }) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = " Đề số ${it.examNo} ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
              Spacer(modifier = Modifier.width(20.dp))
              Row {
                Icon(Icons.Default.CheckCircle, contentDescription = "Close", tint = Color(0xFF0A9204))

                Text(text = "${it.correctAns}",
                  fontSize = 20.sp,
                  style = TextStyle(color = Color(0xFF0A9204), fontWeight = FontWeight.Bold),
                )
              }



              Row {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Red)
                Text(text = "${it.incorrectAns}",
                  fontSize = 20.sp,
                  style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                )

              }

              Text(text = "-- ${it.notAnswer}",
                fontSize = 20.sp,
                style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold),
                )
              Text(text = "/${it.totalQuestion}", fontWeight = FontWeight.Bold, color = Color.Blue, fontSize = 20.sp)
            }

          }
        }
      }
    }
  }

}