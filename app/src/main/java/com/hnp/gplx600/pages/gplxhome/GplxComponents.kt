package com.hnp.gplx600.pages.gplxhome

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hnp.gplx600.R
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.roomdb.QuestionViewModel
import com.hnp.gplx600.theme.GPLXTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GplxComponents {
  @Composable
  fun HomeCard(
    mainTitle: String,
    subTitle: String,
    backgroundStartColor: Color,
    backgroundStopColor: Color,
    icon: Int,
    onClick: () -> Unit,
  ) {
    Card(modifier = Modifier
      .width(200.dp)
      .padding(all = 8.dp),
      shape = RoundedCornerShape(20.dp),
      elevation = CardDefaults.cardElevation(
        defaultElevation = 20.dp
      ),
      colors = CardColors(
        containerColor = Color.LightGray,
        contentColor = Color.Black,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.Gray
      ),
      onClick = {
        onClick.invoke()
      }) {
      Surface(modifier = Modifier.fillMaxSize()) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(
                  backgroundStartColor, // Start color
                  backgroundStopColor  // End color
                )
              )
            )
        ) {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
          ) {
            Text(text = mainTitle, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(text = subTitle, fontSize = 15.sp)
            Image(
              painter = painterResource(id = icon),
              contentDescription = "A1",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .fillMaxWidth()
                .fillMaxHeight()
            )
          }
        }
      }
    }
  }

  @OptIn(DelicateCoroutinesApi::class)
  @Composable
  fun DetailScreen(
    navController: NavController,
    globalVar: GlobalVariable,
    db: AppDataBase,
    vm: QuestionViewModel,
  ) {
    val dataList = vm.getAllQuestion().collectAsState(initial = emptyList())
    var index by remember {
      mutableIntStateOf(0)
    };
    Column(modifier = Modifier.fillMaxSize()) {
      Text(text = "Selected License ${globalVar.currentLicense}| ${dataList.value.size}")
      Row(modifier = Modifier.height(50.dp).align(Alignment.CenterHorizontally)) {
        Button(onClick = {
          GlobalScope.launch(Dispatchers.IO) {
            val question =
              ErpInterface.Question((dataList.value.size), "Nội dung câu hỏi số ${dataList.value.size}", "tip1", 1, 1, 1, 1, 1, 1, 1)
            index++;
            vm.addQuestion(question)
          }
        }, modifier = Modifier.height(50.dp)) {
          Text(text = "Add question")
        }
      }
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(all = 16.dp)
          .height(200.dp)
      ) {
        items(dataList.value) { item ->
          Row {
            Text(text = item.text)
          }
        }
      }

    }

  }

  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  @Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
  @Composable
  fun GreetingPreview() {
    GPLXTheme {
      HomeCard("A1", "200 câu", Color(0xFFB7D7F1), Color(0xFF40DA6B), R.drawable.a1, onClick = {})
    }
  }
}


