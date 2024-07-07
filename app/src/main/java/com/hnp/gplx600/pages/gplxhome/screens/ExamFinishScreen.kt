package com.hnp.gplx600.pages.gplxhome.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.api.bannerID2
import com.hnp.gplx600.pages.gplxhome.components.BannerAd
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.roomdb.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamFinishScreen(
  navController: NavController,
  globalVar: GlobalVariable,
  db: AppDataBase,
  vm: QuestionViewModel,
) {
  val dataList =
    vm.getExamWithQuestionByLicenseAndExamNo(globalVar.currentLicense, globalVar.currentExamNo)
      .collectAsState(initial = emptyList())


  LaunchedEffect(key1 = true) {

  }
  SideEffect {

  }

  BackHandler {
    // Handle the back button press here
    // For example, show a dialog, perform a custom action, etc.
    //navController.popBackStack()
    navController.navigate("examlistscreen") {
      popUpTo("examlistscreen") {
        inclusive = true
      }
    }
  }
  Surface(modifier = Modifier.fillMaxSize(), color = Color.Green) {
    Scaffold(topBar = {
      TopAppBar(modifier = Modifier
        .height(50.dp)
        .background(color = Color.Red), title = {
        Row(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "Kết quả bài thi", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
      }, navigationIcon = {
        IconButton(onClick = {
          navController.navigate("examlistscreen") {
            popUpTo("examlistscreen") {
              inclusive = true
            }
          }
        }) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
      })
    }, bottomBar = {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .background(color = Color(0xFFFFFFFF)),
        contentAlignment = Alignment.Center
      ) {

        BannerAd(bannerAdUnitId = bannerID2)
      }
    }) { paddingValues ->
      Box(
        modifier = Modifier
          .padding(paddingValues)
          .fillMaxSize()
      ) {
        if (dataList.value.isNotEmpty()) ExamSummary(dataList.value, vm, navController, globalVar)
        else Text(text = "Không có câu hỏi nào")
      }
    }
  }

}