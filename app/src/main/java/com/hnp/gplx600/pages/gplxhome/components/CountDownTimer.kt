package com.hnp.gplx600.pages.gplxhome.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun CountDownTimer(startTime: Int)
{
 var timeLeft by remember { mutableIntStateOf(startTime) }
  LaunchedEffect(Unit) {
    while (timeLeft > 0) {
      delay(1000L)
      timeLeft--
    }
  }
  val minutes = TimeUnit.SECONDS.toMinutes(timeLeft.toLong())
  val seconds = timeLeft % 60

  Box(contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize()) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
      Text(text = "Thời gian còn lại: $minutes: $seconds", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(0.dp))
      Spacer (modifier = Modifier.width(16.dp))
      Button(modifier = Modifier
        .padding(0.dp)
        ,
        contentPadding = PaddingValues(
          start = 0.dp,
          top = 0.dp,
          end = 0.dp,
          bottom = 0.dp
        ),
        onClick = { /*TODO*/ }) {
        Text(text = "Nộp bài", fontSize = 12.sp, modifier = Modifier.padding(0.dp))
      }
    }

  }
}