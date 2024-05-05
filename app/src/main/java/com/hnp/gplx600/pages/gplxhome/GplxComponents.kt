package com.hnp.gplx600.pages.gplxhome

import android.os.Build
import android.provider.CalendarContract
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.guru.fontawesomecomposelib.FaIcons.Icons
import com.guru.fontawesomecomposelib.FaIcons.Pager
import com.hnp.gplx600.R
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalFunction
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.roomdb.QuestionViewModel
import com.hnp.gplx600.theme.GPLXTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.withContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import org.json.JSONArray
import org.json.JSONObject

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
    val lct = LocalContext.current
    /*Column(modifier = Modifier.fillMaxSize()) {
      Text(text = "Selected License ${globalVar.currentLicense}| ${dataList.value.size}")
      Row(
        modifier = Modifier
          .height(50.dp)
          .align(Alignment.CenterHorizontally)
      ) {
        Button(onClick = {
          GlobalScope.launch(Dispatchers.IO) {
            GlobalFunction().initDatabase(lct, vm)
          }
        }, modifier = Modifier.height(50.dp)) {
          Text(text = "Add question")
        }
        Button(onClick = {
          GlobalScope.launch(Dispatchers.IO) {
            vm.deleteAllQuestion()
          }
        }, modifier = Modifier.height(50.dp)) {
          Text(text = "Delete question")
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
    }*/
    HorizontalPagerWithBottomNavigation(dataList.value)
  }

  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  @Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
  @Composable
  fun GreetingPreview() {
    GPLXTheme {
      HomeCard("A1", "200 câu", Color(0xFFB7D7F1), Color(0xFF40DA6B), R.drawable.a1, onClick = {})
    }
  }



  @OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class,
    DelicateCoroutinesApi::class
  )
  @Composable
  fun HorizontalPagerWithBottomNavigation(questionList: List<ErpInterface.Question>) {
    val questions = listOf(
      "Question 10",
      "Question 2",
      "Question 3",
      "Question 4",
      "Question 5"
    )

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var currentPageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(pagerState) {
      snapshotFlow { pagerState.currentPage }.collect { page ->
        currentPageIndex = page
      }
    }

    Scaffold(
      bottomBar = {
        /*Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

          Text(text = "Page ${currentPageIndex + 1} of ${questions.size}")
        }*/
        BottomNavigation(
          currentPageIndex = currentPageIndex,
          onPreviousClick = {
            scope.launch {
              pagerState.animateScrollToPage(currentPageIndex - 1)
            }
          }          ,
          onNextClick = {
            scope.launch {
              pagerState.animateScrollToPage(currentPageIndex + 1)
            }
          },
          pageSize = questionList.size

        )
      }
    ) { paddingValues ->
      HorizontalPager(
        count = questionList.size,
        state = pagerState,
        modifier = Modifier
          .padding(paddingValues)
          .fillMaxSize()
      ) { page ->
        QuestionPage(question = questionList[page])
      }
    }
  }

  @Composable
  fun QuestionPage(question: ErpInterface.Question) {
    //create jsonarray from answer array string
    val jsonArray = JSONArray(question.answers)
    val answerList = mutableListOf<JSONObject>()
    for (i in 0 until jsonArray.length()) {
      //add each answer to answerList
      answerList.add(jsonArray.getJSONObject(i))
    }
    var showCorrect by remember { mutableStateOf(false) }

    Column(
      modifier = Modifier.fillMaxSize().background(color = Color.White),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = "Câu ${question.index}: ", modifier = Modifier.padding(16.dp), style = TextStyle(color = Color.Black))
      Text(text = question.text, modifier = Modifier.padding(16.dp), style = TextStyle(color = Color.Black))
      Text(text = "TIP:" + question.tip, modifier = Modifier.padding(16.dp), style = TextStyle(color = Color.Black))
     //show answer
      LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(answerList) { answer ->
          //get answer index
          val index = answerList.indexOf(answer)+1
          val correct = answer.getBoolean("correct")
          //show answer
          Text(text = "$index ${answer.getString("text")}",
            style = TextStyle(color = if (showCorrect) if (correct) Color.Green else Color.Red else Color.Black) ,
            modifier = Modifier
            .padding(8.dp)
            .clickable {
              //change Text above's color to green when answers.correct is true and red when answers.correct is false
              showCorrect = true
            },


          )

        }
      }


    }
  }

  @Composable
  fun BottomNavigation(
    currentPageIndex: Int,
    onPreviousClick:  () -> Unit,
    onNextClick:  () -> Unit,
    pageSize: Int
  ) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
      IconButton(
        onClick = onPreviousClick,
        enabled = currentPageIndex > 0
      ) {
        Icon(imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack , contentDescription = null)
      }
      Text(text = "Câu ${currentPageIndex + 1}/${pageSize}")
      IconButton(
        onClick = onNextClick,
        enabled = currentPageIndex < pageSize - 1
      ) {
        Icon(imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowForward , contentDescription = null)
      }
    }
  }
}


