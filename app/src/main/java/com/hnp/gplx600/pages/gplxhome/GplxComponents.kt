package com.hnp.gplx600.pages.gplxhome

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.hnp.gplx600.R
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.roomdb.QuestionViewModel
import com.hnp.gplx600.theme.GPLXTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
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

  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  @Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
  @Composable
  fun GreetingPreview() {
    GPLXTheme {
      HomeCard("A1", "200 câu", Color(0xFFB7D7F1), Color(0xFF40DA6B), R.drawable.a1, onClick = {})
    }
  }

  @OptIn(ExperimentalPagerApi::class)
  @Composable
  fun HorizontalPagerWithBottomNavigation(
    questionList: List<ErpInterface.Question>,
    vm: QuestionViewModel,
  ) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var currentPageIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState) {
      snapshotFlow { pagerState.currentPage }.collect { page ->
        currentPageIndex = page
      }
    }

    Scaffold(bottomBar = {
      BottomNavigation(currentPageIndex = currentPageIndex, onPreviousClick = {
        scope.launch {
          if (currentPageIndex > 0) {
            pagerState.animateScrollToPage(currentPageIndex - 1)
          } else {
            pagerState.animateScrollToPage(questionList.size - 1)
          }
        }
      }, onNextClick = {
        scope.launch {
          if (currentPageIndex < questionList.size - 1) {
            pagerState.animateScrollToPage(currentPageIndex + 1)
          } else {
            pagerState.animateScrollToPage(0)
          }
        }
      }, pageSize = questionList.size

      )
    }) { paddingValues ->
      HorizontalPager(
        count = questionList.size,
        state = pagerState,
        modifier = Modifier
          .padding(paddingValues)
          .fillMaxSize()
      ) { page ->
        QuestionPage(question = questionList[page], vm = vm)
      }
    }
  }

  @Composable
  fun ImageFromUrl(imageUrl: String) {
    val painter = // You can customize image loading options here if needed
      rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
          .apply(block = fun ImageRequest.Builder.() {
            // You can customize image loading options here if needed
          }).build()
      )
    Image(
      painter = painter,
      contentDescription = null, // You can provide a content description here if needed
      modifier = Modifier.fillMaxSize(),
      // You can specify other parameters for the Image composable here
    )
  }

  @SuppressLint("DiscouragedApi")
  @Composable
  fun QuestionPage(question: ErpInterface.Question, vm: QuestionViewModel) {
    //create jsonarray from answer array string
    val jsonArray = JSONArray(question.answers)
    val answerList = mutableListOf<JSONObject>()
    for (i in 0 until jsonArray.length()) {
      //add each answer to answerList
      answerList.add(jsonArray.getJSONObject(i))
    }
    var showCorrect by remember { mutableStateOf(question.currentAnswer != -1) }
    var showTip by remember { mutableStateOf(false) }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFF8EFE0)),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "Câu ${question.index} ",
        fontSize = 25.sp,
        modifier = Modifier.padding(16.dp),
        style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold)
      )
      Text(
        text = question.text,
        fontSize = 20.sp,
        modifier = Modifier.padding(16.dp),
        style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold)
      )
      //show answer
      LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(answerList) { answer ->
          //get answer index
          val index = answerList.indexOf(answer) + 1
          val correct = answer.getBoolean("correct")
          //show answer
          Box(
            modifier = Modifier
              .padding(5.dp)
              .background(color = Color.White)
              .fillMaxSize()
              .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
              .clickable {
                showCorrect = true
                vm.updateAnswer(question.index, index)
              }
              .shadow(1.dp),
          ) {
            Text(
              text = "$index. ${answer.getString("text")}",
              fontSize = 18.sp,
              style = TextStyle(color = if (showCorrect) if (correct) Color(0xFF0A9204) else Color.Red else Color.Black),
              modifier = Modifier.padding(8.dp)
            )
          }
        }
      }
      if (showTip && question.tip.isNotEmpty()) Text(
        text = question.tip,
        fontSize = 18.sp,
        modifier = Modifier.padding(16.dp),
        style = TextStyle(color = Color.Blue, fontStyle = FontStyle.Italic),
      )
      if (question.tip.isNotEmpty()) Text(
        text = "Show Tip", fontSize = 15.sp, modifier = Modifier
          .padding(0.dp)
          .clickable {
            showTip = true
          }, style = TextStyle(fontStyle = FontStyle.Italic)
      )
      Spacer(modifier = Modifier.height(30.dp))
      val context = LocalContext.current
      val res = context.resources
      val packageName = context.packageName
      //val drawableName = question.image
      val drawableName = question.image.split(".").first()
      val resourceId = res.getIdentifier(drawableName, "drawable", packageName)
      if (question.image.isNotEmpty()) Image(
        painter = painterResource(id = resourceId),
        contentDescription = null,
        modifier = Modifier
          .fillMaxWidth()
          .padding(all = 8.dp)
          .clip(RoundedCornerShape(5.dp)),
        contentScale = ContentScale.Crop
      )
//      if (question.image.isNotEmpty()) ImageFromUrl(imageUrl = "https://gplx.app/images/questions/" + question.image)

    }
  }

  @Composable
  fun BottomNavigation(
    currentPageIndex: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    pageSize: Int,
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxWidth()
    ) {
      IconButton(
        onClick = onPreviousClick, enabled = true //currentPageIndex > 0
      ) {
        Icon(
          imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = null
        )
      }
      Text(text = "Câu ${currentPageIndex + 1}/${pageSize}")
      IconButton(
        onClick = onNextClick, enabled = true //currentPageIndex < pageSize - 1
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null
        )
      }
    }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun GPLXOptionScreen(navController: NavController, globalVar: GlobalVariable) {
    //Add a list of options using Lazy Colum
    val options = listOf(
      ErpInterface.OptionScreenData(
        id = -2,
        title = "Vào thi ngay",
        icon = { FaIcon(faIcon = FaIcons.Telegram, size = 24.dp, tint = Color(0xFF0088CC)) },
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
    Scaffold(
      topBar = {
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
            Text(text = "Chọn option (Hạng ${globalVar.currentLicense})")
          }
        }, navigationIcon = {
          IconButton(onClick = { navController.navigateUp() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        })
      },
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
              .height(80.dp)
              .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
              //border radius
              .clip(RoundedCornerShape(10.dp))
              .background(
                brush = Brush.verticalGradient(
                  colors = listOf(
                    Color(0xFFE7F375), // Start color
                    Color(0xFFB7D7F1)  // End color
                  )
                )
              )
              .clickable {
                globalVar.changeTopic(option.id)
                when (option.id) {
                  -2 -> {
                    navController.navigate("examselectionscreen") {
                      popUpTo("examselectionscreen") {
                        inclusive = true
                      }
                    }
                  }

                  else -> {
                    navController.navigate("detailscreen") {
                      popUpTo("detailscreen") {
                        inclusive = true
                      }
                    }

                  }
                }
              }) {
            //show icon and title
            Spacer(modifier = Modifier.width(10.dp))
            option.icon()
            Text(
              text = option.title,
              fontSize = 20.sp,
              modifier = Modifier.padding(16.dp),
              color = Color(0xFF1380B9)
            )
          }

        }
      }
    }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun GPLXExamSelectScreen(
    navController: NavController,
    globalVar: GlobalVariable,
    vm: QuestionViewModel,
  ) {
    val items: List<Int> = (0..10).toList()
    Scaffold(
      topBar = {
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
            Text(text = "Chọn đề thi")
          }
        }, navigationIcon = {
          IconButton(onClick = { navController.navigateUp() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        })
      },
      ) { paddingValues ->
      LazyColumn(modifier = Modifier.padding(paddingValues)) {
        itemsIndexed(items) { index, item ->
          Column(modifier = Modifier.fillMaxWidth()) {
            ExamWidget(examNo = item)
            Spacer(modifier = Modifier.height(10.dp))
          }
        }
      }
    }
  }

  @Composable
  fun ExamWidget(examNo: Int) {
    Box(modifier = Modifier
      .fillMaxWidth()
      .height(60.dp)
      .background(color = Color.Gray)
      .clickable {

      }) {
      if(examNo == 0){
        Text(text = "Đề thi ngẫu nhiên")
      }
       else
      Text(text = "Đề thi số $examNo")
    }
  }

  @Composable
  fun BannerAd(
    modifier: Modifier = Modifier,
    bannerAdUnitId: String = "ca-app-pub-3940256099942544/9214589741",
  ) {
    AndroidView(modifier = modifier, factory = { context ->
      AdView(context).apply {
        setAdSize(AdSize.BANNER)
        adUnitId = bannerAdUnitId
        loadAd(AdRequest.Builder().build())
      }
    })
  }

}


