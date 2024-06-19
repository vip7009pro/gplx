package com.hnp.gplx600.pages.gplxhome.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.roomdb.QuestionViewModel
import org.json.JSONArray
import org.json.JSONObject

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

@SuppressLint("DiscouragedApi")
@Composable
fun QuestionPage2(question: ErpInterface.ExamQuestionByLicenseAndExamNo, vm: QuestionViewModel) {
  //create jsonarray from answer array string
  val jsonArray = JSONArray(question.answers)
  val answerList = mutableListOf<JSONObject>()
  for (i in 0 until jsonArray.length()) {
    //add each answer to answerList
    answerList.add(jsonArray.getJSONObject(i))
  }
  //Log.d("answer list", answerList.toString())
  var showCorrect by remember { mutableStateOf(question.examAnswer != -1) }
  var showTip by remember { mutableStateOf(false) }


  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(color = Color(0x72C8F0AD)),
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Câu ${question.questionNo} ",
      fontSize = 25.sp,
      modifier = Modifier.padding(5.dp),
      style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold)
    )
    Text(
      text = question.text,
      fontSize = 20.sp,
      modifier = Modifier.padding(5.dp),
      style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold)
    )
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
    //show answer
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      items(answerList) { answer ->
        //get answer index
        val index = answerList.indexOf(answer) + 1
        val correct = answer.getBoolean("correct")
        //show answer
        Box(
          modifier = Modifier
            .padding(5.dp)
            .background(color = if (index == question.examAnswer)  Color(0xFF8CC7F5) else Color.White)
            .fillMaxSize()
//            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
            .clickable {
              showCorrect = true
              vm.updateAnswerByExamIndex(question.index, index)
            }
            .shadow(1.dp),
        ) {
          Text(
            text = "$index. ${answer.getString("text")}",
            fontSize = 18.sp,
            color =  Color.Black,
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
    //Spacer(modifier = Modifier.height(30.dp))


//      if (question.image.isNotEmpty()) ImageFromUrl(imageUrl = "https://gplx.app/images/questions/" + question.image)

  }
}
