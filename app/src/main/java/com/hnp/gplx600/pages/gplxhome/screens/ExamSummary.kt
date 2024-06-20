package com.hnp.gplx600.pages.gplxhome.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.components.MyDialog
import com.hnp.gplx600.roomdb.QuestionViewModel
import org.json.JSONArray
import org.json.JSONObject

@SuppressLint("DiscouragedApi")
@Composable
fun QuestionPage3(question: ErpInterface.ExamQuestionByLicenseAndExamNo, vm: QuestionViewModel) {
  val jsonArray = JSONArray(question.answers)
  val answerList = mutableListOf<JSONObject>()
  for (i in 0 until jsonArray.length()) {
    //add each answer to answerList
    answerList.add(jsonArray.getJSONObject(i))
  }
  var showAnswer by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .padding(5.dp)
      .fillMaxSize()
      .clip(RoundedCornerShape(8.dp))
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(
            Color(0xFFE5ECEC), // Start color
            Color(0xFFEEF8F8)  // End color
          )
        )
      )
      .clickable {
        showAnswer = !showAnswer
      },
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start
    ) {
      val isCorrect = question.dapAn == question.examAnswer
      Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "Câu ${question.questionNo} ",
          fontSize = 15.sp,
          style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
          modifier = Modifier.clickable {
            showAnswer = !showAnswer
          })
        Spacer(modifier = Modifier.width(10.dp))
        //icon ok
        if (question.examAnswer != -1) {
          if (isCorrect) {
            Text(text = "✓",
              fontSize = 20.sp,
              style = TextStyle(color = Color(0xFF0A9204), fontWeight = FontWeight.Bold),
              modifier = Modifier.clickable {
                showAnswer = !showAnswer
              })
          } else {
            Text(text = "X",
              fontSize = 20.sp,
              style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
              modifier = Modifier.clickable {
                showAnswer = !showAnswer
              })
          }

        } else Text(text = "--",
          fontSize = 20.sp,
          style = TextStyle(color = Color(0xFF447AD8), fontWeight = FontWeight.Bold),
          modifier = Modifier.clickable {
            showAnswer = !showAnswer
          })
      }

      Text(
        text = question.text, fontSize = 15.sp, modifier = Modifier
          .padding(5.dp)
          .clickable {
            showAnswer = !showAnswer
          }, style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold)
      )

      if (showAnswer) {
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
        Column(modifier = Modifier.fillMaxSize()) {
          answerList.map { answer ->
            val index = answerList.indexOf(answer) + 1
            val correct = question.dapAn == question.examAnswer

            Box(
              modifier = Modifier
                .padding(2.dp)
                .background(
                  color = if (index == question.examAnswer) {
                    if (correct) Color(0xFF55E04F) else Color(0xFFF05252)
                  } else if (index == question.dapAn) Color(0xFF55E04F)
                  else Color.White
                )
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)
                )

            ) {
              Row(
                modifier = Modifier
                  .fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

              ) {
                Text(
                  text = "$index. ${answer.getString("text")}",
                  fontSize = 15.sp,
                  style = TextStyle(color = if (index == question.dapAn) Color.Black else Color.Black),
                  modifier = Modifier.padding(8.dp)
                )
              }
            }

          }

        }
        if (question.tip.isNotEmpty()) Text(
          text = question.tip,
          fontSize = 12.sp,
          modifier = Modifier.padding(16.dp),
          style = TextStyle(color = Color.Blue, fontStyle = FontStyle.Italic),
        )

      }

      //      if (question.image.isNotEmpty()) ImageFromUrl(imageUrl = "https://gplx.app/images/questions/" + question.image)

    }
  }
}

@Composable
fun ExamSummary(
  questionList: List<ErpInterface.ExamQuestionByLicenseAndExamNo>,
  vm: QuestionViewModel,
  navController: NavController,
  globalVar: GlobalVariable
) {
  //summary exam score with number of correct and incorrect answer
  val correctAnswer = questionList.count { it.examAnswer == it.dapAn }
  val incorrectAnswer = questionList.count { it.examAnswer != it.dapAn && it.examAnswer != -1 }
  val notAnswered = questionList.count { it.examAnswer == -1 }
  val requiredQuestionFailed =
    questionList.count { it.required == 1 && it.examAnswer != -1 && it.dapAn != it.examAnswer }
  val totalQuestion = questionList.size
  val requiredCorrectAnswerByLicense = when (questionList[0].license) {
    "A1" -> 21
    "A2" -> 23
    "A3" -> 23
    "A4" -> 21
    "B1" -> 27
    "B2" -> 32
    else -> 36
  }
  //println("required correct=$requiredCorrectAnswerByLicense")
  Box(modifier = Modifier.fillMaxSize()) {
    Column(
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFD4D3D8))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(all = 2.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(
            brush = Brush.verticalGradient(
              colors = listOf(
                Color(0xFF97F353), // Start color
                Color(0xFFEEF8F8)  // End color
              )
            )
          ),
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
          horizontalArrangement = Arrangement.SpaceAround,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "Kết quả:", color = Color.Blue)
          if (requiredQuestionFailed > 0) {
            Text(
              text = "Thi trượt", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 25.sp
            )
          } else {
            if (correctAnswer >= requiredCorrectAnswerByLicense) Text(
              text = "Thi đỗ",
              color = Color(0xFF0A9204),
              fontWeight = FontWeight.Bold,
              fontSize = 25.sp
            )
            else Text(
              text = "Thi trượt", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
          }
          Text(text = "Thi lại", color = Color.Blue, modifier = Modifier.clickable {

            globalVar.showDialog(
              dialogTitle = "Thông báo",
              dialogText = "Bạn có chắc chắn muốn thi lại?",
              dialogCat = "",
              dlConfirm = {
                vm.resetExamAnswer(questionList[0].license, questionList[0].examNo)
                navController.navigateUp()
              },
              dlCancel = {
                //navController.navigateUp()
              }
            )

          })
        }
        Row(//align center
          modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
          horizontalArrangement = Arrangement.SpaceAround,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "Tổng: $totalQuestion", color = Color.Blue)
          Text(text = "Đúng ✓: $correctAnswer", color = Color(0xFF0A9204))
          Text(text = "Sai X: $incorrectAnswer", color = Color.Red)
          Text(text = "Chưa làm: $notAnswered", color = Color.Gray)
        }

      }

      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(5.dp)
      ) {
        //indexed items
        items(questionList.size) { index ->
          QuestionPage3(question = questionList[index], vm = vm)
          Spacer(modifier = Modifier.height(2.dp))
          //Text(text = "Question $index")
        }
      }


    }

  }
  MyDialog().FNDialog(globalVar = globalVar)

}