package com.hnp.gplx600.pages.gplxhome

import android.os.Build
import android.provider.CalendarContract.Colors
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hnp.gplx600.R
import com.hnp.gplx600.theme.GPLXTheme

class GplxHomeCard {
  @Composable
  fun HomeCard() {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(all = 8.dp),


      shape = RoundedCornerShape(20.dp),
      elevation = CardDefaults.cardElevation(
        defaultElevation = 20.dp
      ),
      colors = CardColors(
        containerColor = Color.Yellow,
        contentColor = Color.Black,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.Gray
      ),
    ) {

      Box(modifier = Modifier.padding(all= 10.dp).aspectRatio(2f)) {
        Column {
          Button(
            onClick = { /* Xử lý sự kiện click */ }

          ) {
            Text(text = "Click here")
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
      Column {
        HomeCard()
        HomeCard()
        HomeCard()
        HomeCard()

      }

    }
  }
}


