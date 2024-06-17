package com.hnp.gplx600.pages.gplxhome.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
