package com.hnp.gplx600.pages.gplxhome.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.pages.gplxhome.components.BannerAd

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GPLXOptionScreen(navController: NavController, globalVar: GlobalVariable) {
  //Add a list of options using Lazy Colum
  var options by remember { mutableStateOf(emptyList<ErpInterface.OptionScreenData>())}
  LaunchedEffect(key1 = 1) {
    options = listOf(
      ErpInterface.OptionScreenData(
        id = -2,
        title = "Vào thi ngay",
        icon = { FaIcon(faIcon = FaIcons.Check, size = 24.dp, tint = Color.Blue) },
      ),
      ErpInterface.OptionScreenData(
        id = -1,
        title = "Tất cả câu hỏi",
        icon = { FaIcon(faIcon = FaIcons.Question, size = 24.dp, tint = Color.Blue) },
      ),
      ErpInterface.OptionScreenData(
        id = 0,
        title = "Các câu điểm liệt",
        icon = { FaIcon(faIcon = FaIcons.Ban, size = 24.dp, tint = Color.Red) },
      ),
      ErpInterface.OptionScreenData(
        id = 1,
        title = "Chương 1: Khái niệm vào quy tắc",
        icon = { FaIcon(faIcon = FaIcons.Atom, size = 24.dp, tint = Color.Black) },
      ),
      ErpInterface.OptionScreenData(
        id = 2,
        title = "Chương 2: Nghiệp vụ vận tải",
        icon = { FaIcon(faIcon = FaIcons.Truck, size = 24.dp, tint = Color(0xFF0AB334)) },
      ),
      ErpInterface.OptionScreenData(
        id = 3,
        title = "Chương 3: Văn hóa và đạo đức lái xe",
        icon = { FaIcon(faIcon = FaIcons.ThumbsUp, size = 24.dp, tint = Color(0xFFF71993)) },
      ),
      ErpInterface.OptionScreenData(
        id = 4,
        title = "Chương 4: Kỹ thuật lái xe",
        icon = { FaIcon(faIcon = FaIcons.CarSide, size = 24.dp, tint = Color(0xFF8F4040)) },
      ),
      ErpInterface.OptionScreenData(
        id = 5,
        title = "Chương 5: Cấu tạo và sửa chữa",
        icon = { FaIcon(faIcon = FaIcons.FirstAid, size = 24.dp, tint = Color(0xFF9B20E7)) },
      ),
      ErpInterface.OptionScreenData(
        id = 6,
        title = "Chương 6: Biển báo đường bộ",
        icon = { FaIcon(faIcon = FaIcons.Sign, size = 24.dp, tint = Color(0xFFF75F19)) },
      ),
      ErpInterface.OptionScreenData(
        id = 7,
        title = "Chương 7: Sa hình",
        icon = { FaIcon(faIcon = FaIcons.Cross, size = 24.dp, tint = Color(0xFF2D47D8)) },
      ),
    )
  }
  Scaffold(
    topBar = {
      TopAppBar(modifier = Modifier
        .height(50.dp)
        .background(color = Color.Green), title = {
        Row(
          modifier = Modifier
            .fillMaxSize()
            .height(40.dp),
          horizontalArrangement = Arrangement.Start,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "Chọn option (${globalVar.currentLicense})")
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
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      items(options) { option ->
        Row(horizontalArrangement = Arrangement.Start,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 3.dp)
            .height(60.dp)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
            //border radius
            .clip(RoundedCornerShape(10.dp))
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(
                  Color(0xFFB7D7F1), // Start color
                  Color(0xFFB7D7F1)  // End color
                )
              )
            )
            .clickable {
              if (option.id == -2) {
                navController.navigate("examlistscreen") {
                  popUpTo("examlistscreen") {
                    inclusive = true
                  }
                }
              } else {
                navController.navigate("detailscreen") {
                  popUpTo("detailscreen") {
                    inclusive = true
                  }
                }
              }
              globalVar.changeTopic(option.id)
            }) {
          //show icon and title
          Spacer(modifier = Modifier.width(10.dp))
          option.icon()
          Text(
            text = option.title,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp),
            color = Color(0xFF1380B9)
          )
        }
      }
    }
  }

}
