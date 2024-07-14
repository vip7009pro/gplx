package com.hnp.gplx600.pages.gplxhome.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.roomdb.QuestionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerWithBottomNavigation(
  questionList: List<ErpInterface.Question>,
  vm: QuestionViewModel,
) {
  val pagerState = rememberPagerState(
    initialPage = 0,
  )
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerWithBottomNavigation2(
  questionList: List<ErpInterface.ExamQuestionByLicenseAndExamNo>,
  vm: QuestionViewModel,
) {
  val pagerState = rememberPagerState(
    initialPage = 0,
  )
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
    ) { page ->  QuestionPage2(question = questionList[page], vm = vm)
    }
  }
}
