package com.hnp.gplx600.pages.gplxhome.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    .width(180.dp)
    .fillMaxHeight()
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