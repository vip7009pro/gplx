@file:Suppress("NAME_SHADOWING")

package com.hnp.gplx600.pages.gplxhome.screens

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.components.MyDialog
import com.hnp.gplx600.pages.gplxhome.components.BannerAd
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.roomdb.QuestionViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamListScreen(
  navController: NavController,
  db: AppDataBase,
  vm: QuestionViewModel,
  globalVar: GlobalVariable,
  ct: Context
) {
  val examNo = vm.getExamNo(globalVar.currentLicense).collectAsState(initial = emptyList())
  val dataList = vm.getAllQuestion().collectAsState(initial = emptyList())
  val latestExamNo = if (examNo.value.isNotEmpty()) examNo.value[0].maxExamNo else 0
  val examList = vm.getExamWithQuestionByLicense(globalVar.currentLicense).collectAsState(initial = emptyList())
  var filteredList by remember { mutableStateOf(emptyList<ErpInterface.Question>()) }
  var requiredQuestionList by remember { mutableStateOf(emptyList<ErpInterface.Question>()) }
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
  requiredQuestionList = filteredList.filter { ele->
    ele.required == 1 && ele.topic == 1
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
  val examNoList = examList.value.sortedBy { it.examNo }

  var mInterstitialAd: InterstitialAd? = null
  fun loadInterstitialAd(context: Context) {
    InterstitialAd.load(context, "ca-app-pub-3940256099942544/1033173712", AdRequest.Builder().build(),
      object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
          mInterstitialAd = null
        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
          mInterstitialAd = interstitialAd
        }
      }
    )
  }
  fun showInterstitialAd(context: Context, onAdDismissed: () -> Unit, onAddNull: ()-> Unit) {
    if (mInterstitialAd != null) {
      mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdFailedToShowFullScreenContent(e: AdError) {
          mInterstitialAd = null
        }

        override fun onAdDismissedFullScreenContent() {
          mInterstitialAd = null
          loadInterstitialAd(context)
          onAdDismissed()
        }
      }
      mInterstitialAd?.show(context as Activity)
    }
    else {
      onAddNull()
    }
  }
/*  suspend fun loadExamNo()
  {
    vm.getExamNo(globalVar.currentLicense).collect {
      examNo1 = it
      latestExamNo1 = if (examNo1.isNotEmpty()) examNo1[0].maxExamNo else 0
    }
  }
  suspend fun loadDataList()
  {
    Log.d("xxx","vao day r")
    vm.getAllQuestion().collect { data ->
      dataList1 = data
      Log.d("dataList1", dataList1.toString())
      when (globalVar.currentLicense) {
        "A1" -> {
          filteredList = dataList1.filter {
            it.a1 != 0
          }
        }

        "A2" -> {
          filteredList = dataList1.filter {
            it.a2 != 0
          }
        }

        "A3" -> {
          filteredList = dataList1.filter {
            it.a3 != 0
          }
        }

        "A4" -> {
          filteredList = dataList1.filter {
            it.a4 != 0
          }
        }

        "B1" -> {
          filteredList = dataList1.filter {
            it.b1 != 0
          }
        }

        else -> {
          filteredList = dataList1
        }
      }
      requiredQuestionList1 = filteredList.filter { ele->
        ele.required == 1 && ele.topic == 1
      }
    }


  }
  suspend fun loadExamList()
  {
    vm.getExamWithQuestionByLicense(globalVar.currentLicense).collect { ele ->
      examList1 = ele
      examNoList1 = ele.sortedBy { it.examNo }
    }
  }*/

  val ct = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  LaunchedEffect(Unit) {
   /* try {
      loadInterstitialAd(ct)
      loadExamNo()
      loadDataList()
      loadExamList()

    }
    catch (e: Exception)
    {
      Log.d("xxx",e.toString())
    }*/
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
  },
    bottomBar = {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .background(color = Color(0xFFFFFFFF)),
        contentAlignment =  Alignment.Center
      ) {

        BannerAd()
      }
    }
    ) { paddingValues ->
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
          .fillMaxWidth(), onClick = {
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

          finalExamList.forEachIndexed { index, element ->
            vm.addExam(
              ErpInterface.Exam(
                examIndex = 0,
                license = globalVar.currentLicense,
                examNo = latestExamNo + 1,
                questionNo = index + 1,
                index = element.index
              )
            )
          }

        }) {

          Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
          ) {
            Text(text = "Tạo đề mới")
          }
        }
      }
      LazyColumn(
        modifier = Modifier.fillMaxSize()
      ) {
        items(examNoList) {
          //create card show exam name and exam id, and the number of question
          Card(modifier = Modifier.padding(5.dp), onClick = {
            globalVar.currentExamNo = it.examNo
            coroutineScope.launch {
              showInterstitialAd(ct, {
                navController.navigate("examscreen") {
                  popUpTo("examscreen") {
                    inclusive = true
                  }
                }
                //Toast.makeText(ct, "Interstitial Ad Shown!", Toast.LENGTH_SHORT).show()
              }, {
                navController.navigate("examscreen") {
                  popUpTo("examscreen") {
                    inclusive = true
                  }
                }
              })
            }

          }) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = " Đề số ${it.examNo} ", fontWeight = FontWeight.Bold, fontSize = 15.sp
                )
                Text(
                  text = "Tiến độ: ${(100 - it.notAnswer * 1.0f / it.totalQuestion * 100).toInt()}%",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Câu đúng:",
                  fontSize = 15.sp,
                  style = TextStyle(color = Color(0xFF0A9204), fontWeight = FontWeight.Bold),
                )
                Text(
                  text = "Câu sai:",
                  fontSize = 15.sp,
                  style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                )
                Text(
                  text = "Chưa làm:",
                  fontSize = 15.sp,
                  style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold),
                )

              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "${it.correctAns}",
                  fontSize = 15.sp,
                  style = TextStyle(color = Color(0xFF0A9204), fontWeight = FontWeight.Bold),
                )
                Text(
                  text = "${it.incorrectAns}",
                  fontSize = 15.sp,
                  style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                )
                Text(
                  text = "${it.notAnswer}",
                  fontSize = 15.sp,
                  style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold),
                )

              }



              Text(
                text = "Total: ${it.totalQuestion}",
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 15.sp
              )

              IconButton(onClick = {

                //confirm delete exam
                globalVar.showDialog(dialogTitle = "Thông báo",
                  dialogText = "Bạn có chắc chắn muốn xóa đề không?",
                  dialogCat = "",
                  dlConfirm = {
                    vm.deleteExam(it.examNo, globalVar.currentLicense)
                  },
                  dlCancel = {

                  })

              }) {
                Icon(Icons.Default.Clear, contentDescription = "Close", tint = Color.Black)
              }
            }

          }
        }
      }
    }
    MyDialog().FNDialog(globalVar = globalVar)
  }

}