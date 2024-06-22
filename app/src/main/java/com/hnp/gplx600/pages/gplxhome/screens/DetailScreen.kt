package com.hnp.gplx600.pages.gplxhome.screens

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
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.pages.gplxhome.components.BannerAd
import com.hnp.gplx600.pages.gplxhome.components.HorizontalPagerWithBottomNavigation
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.roomdb.QuestionViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
  navController: NavController,
  globalVar: GlobalVariable,
  db: AppDataBase,
  vm: QuestionViewModel,
) {
  val dataList = vm.getAllQuestion().collectAsState(initial = emptyList())
  var filteredList: List<ErpInterface.Question> = emptyList()
  when (globalVar.currentLicense) {
    "A1" -> {
      filteredList = when (globalVar.currentTopic) {
        -1 -> {
          dataList.value.filter {
            it.a1 != 0
          }
        }

        0 -> {
          dataList.value.filter {
            it.a1 != 0 && it.required == 1
          }
        }

        else -> {
          dataList.value.filter {
            it.a1 != 0 && it.topic == globalVar.currentTopic
          }
        }
      }
    }

    "A2" -> {
      filteredList = when (globalVar.currentTopic) {
        -1 -> {
          dataList.value.filter {
            it.a2 != 0
          }
        }

        0 -> {
          dataList.value.filter {
            it.a2 != 0 && it.required == 1
          }
        }

        else -> {
          dataList.value.filter {
            it.a2 != 0 && it.topic == globalVar.currentTopic
          }
        }
      }
    }

    "A3" -> {
      filteredList = when (globalVar.currentTopic) {
        -1 -> {
          dataList.value.filter {
            it.a3 != 0
          }
        }

        0 -> {
          dataList.value.filter {
            it.a3 != 0 && it.required == 1
          }
        }

        else -> {
          dataList.value.filter {
            it.a3 != 0 && it.topic == globalVar.currentTopic
          }
        }
      }
    }

    "A4" -> {
      filteredList = when (globalVar.currentTopic) {
        -1 -> {
          dataList.value.filter {
            it.a4 != 0
          }
        }

        0 -> {
          dataList.value.filter {
            it.a4 != 0 && it.required == 1
          }
        }

        else -> {
          dataList.value.filter {
            it.a4 != 0 && it.topic == globalVar.currentTopic
          }
        }
      }
    }

    "B1" -> {
      filteredList = when (globalVar.currentTopic) {
        -1 -> {
          dataList.value.filter {
            it.b1 != 0
          }
        }

        0 -> {
          dataList.value.filter {
            it.b1 != 0 && it.required == 1
          }
        }

        else -> {
          dataList.value.filter {
            it.b1 != 0 && it.topic == globalVar.currentTopic
          }
        }
      }
    }

    else -> {
      filteredList = when (globalVar.currentTopic) {
        -1 -> {
          dataList.value
        }

        0 -> {
          dataList.value.filter {
            it.required == 1
          }
        }

        else -> {
          dataList.value.filter {
            it.topic == globalVar.currentTopic
          }
        }
      }
    }

  }
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
        Text(text = "Bài thi chi tiết")
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
        contentAlignment = Alignment.Center
      ) {

        BannerAd()
      }
    }) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      if (filteredList.isNotEmpty()) HorizontalPagerWithBottomNavigation(
        filteredList, vm
      ) else Text(
        text = "Không có câu hỏi nào"
      )
    }
  }
}