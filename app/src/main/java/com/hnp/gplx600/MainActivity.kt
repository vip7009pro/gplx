package com.hnp.gplx600

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.Room
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.ads.MobileAds
import com.hnp.gplx600.adsmanager.AppOpenManager
import com.hnp.gplx600.api.GlobalFunction
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.components.Components
import com.hnp.gplx600.pages.gplxhome.screens.DetailScreen
import com.hnp.gplx600.pages.gplxhome.screens.ExamFinishScreen
import com.hnp.gplx600.pages.gplxhome.screens.ExamListScreen
import com.hnp.gplx600.pages.gplxhome.screens.ExamScreen
import com.hnp.gplx600.pages.gplxhome.screens.GPLXOptionScreen
import com.hnp.gplx600.pages.gplxhome.screens.MyHome
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.roomdb.QuestionViewModel
import com.hnp.gplx600.theme.GPLXTheme

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
  private lateinit var appOpenManager: AppOpenManager

  private val db by lazy {
    Room.databaseBuilder(
      context = applicationContext, klass = AppDataBase::class.java, name = "gplx_database"
    ).fallbackToDestructiveMigration().build()
  }
  private val viewModel by viewModels<QuestionViewModel>(factoryProducer = {
    object : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionViewModel(db.questionDao) as T
      }
    }
  })



  @RequiresApi(Build.VERSION_CODES.Q)
  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //MobileAds.initialize(this)
    appOpenManager = AppOpenManager(application as MyApplication)
    setContent {
      GPLXTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
          MainApp(db = db, vm = viewModel,context = this)
        }
      }
    }
  }
}

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainApp(db: AppDataBase, vm: QuestionViewModel, context: Context) {
  val navController = rememberAnimatedNavController()
  val globalVar = viewModel<GlobalVariable>()
  val lct = LocalContext.current

  LaunchedEffect(key1 = true) {
    GlobalFunction().initDatabase(lct, vm)
  }
  NavHost(navController = navController, startDestination = "home") {
    composable("login") {
      Components().LoginScreen(navController = navController, globalVar = globalVar)
    }
    composable("home",
      enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
      ) {
      MyHome(navController = navController, globalVar = globalVar, vm = vm)
    }
    composable("detailscreen",
      enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
    )  {
      DetailScreen(
        navController = navController, globalVar = globalVar, db = db, vm = vm
      )
    }
    composable("optionscreen",
      enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
    )  {
      GPLXOptionScreen(navController = navController, globalVar = globalVar)
    }
    composable("examlistscreen",
      enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
    )  {
      ExamListScreen(navController = navController, globalVar = globalVar, db = db, vm = vm, ct = context)
    }
    composable("examscreen",
      enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
    )  {
      ExamScreen(navController = navController, globalVar = globalVar, db = db, vm = vm, context= context)
    }
    composable("examfinishscreen",
      enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
    )  {
      ExamFinishScreen(navController = navController, globalVar = globalVar, db = db, vm = vm)
    }
  }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
fun GreetingPreview() {
  GPLXTheme {

  }
}
