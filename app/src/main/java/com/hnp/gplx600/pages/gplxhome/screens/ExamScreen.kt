package com.hnp.gplx600.pages.gplxhome.screens

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.api.bannerID4
import com.hnp.gplx600.pages.gplxhome.components.BannerAd
import com.hnp.gplx600.pages.gplxhome.components.CountDownTimer
import com.hnp.gplx600.pages.gplxhome.components.HorizontalPagerWithBottomNavigation2
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.roomdb.QuestionViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
  navController: NavController,
  globalVar: GlobalVariable,
  db: AppDataBase,
  vm: QuestionViewModel,
  context: Context
) {
  val dataList = vm.getExamWithQuestionByLicenseAndExamNo(globalVar.currentLicense, globalVar.currentExamNo).collectAsState(initial = emptyList())
  globalVar.currentDataList = dataList.value
  LaunchedEffect(key1 = true) {

  }

  BackHandler {
    // Handle the back button press here
    // For example, show a dialog, perform a custom action, etc.
    //navController.popBackStack()
    globalVar.showDialog(
      dialogTitle = "Thông báo",
      dialogText = "Chưa hết giờ, Bạn có chắc chắn muốn thoát?",
      dialogCat = "",
      dlConfirm = {
        navController.navigate("examfinishscreen") {
          popUpTo("examfinishscreen") {
            inclusive = true
          }
        }
      },
      dlCancel = {
        //navController.navigateUp()
      }
    )
  }

  Scaffold(topBar = {
    TopAppBar(modifier = Modifier
      .height(50.dp)
      .background(color = Color.Green), title = {
      Row(
        modifier = Modifier
          .fillMaxSize(),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
      ) {
        CountDownTimer(startTime = 1200, navController=navController, globalVar = globalVar, ct = context)
      }
    }, navigationIcon = {
      IconButton(onClick = {
        globalVar.showDialog(
          dialogTitle = "Thông báo",
          dialogText = "Chưa hết giờ, Bạn có chắc chắn muốn thoát?",
          dialogCat = "",
          dlConfirm = {
            navController.navigate("examfinishscreen") {
              popUpTo("examfinishscreen") {
                inclusive = true
              }
            }
          },
          dlCancel = {
            //navController.navigateUp()
          }
        )

      }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
      }
    },
      )
  }, bottomBar =  {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(color = Color(0xFFFFFFFF)),
      contentAlignment =  Alignment.Center
    ) {

      BannerAd(bannerAdUnitId = bannerID4)
    }
  }) {
      paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      if (dataList.value.isNotEmpty()) HorizontalPagerWithBottomNavigation2(globalVar.currentDataList, vm)
      else Text(text = "Không có câu hỏi nào")
    }
  }
}