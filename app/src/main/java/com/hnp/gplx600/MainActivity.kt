package com.hnp.gplx600

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.hnp.gplx600.api.GlobalFunction
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.components.Components
import com.hnp.gplx600.pages.gplxhome.GplxComponents
import com.hnp.gplx600.pages.gplxhome.GplxHome
import com.hnp.gplx600.roomdb.AppDataBase
import com.hnp.gplx600.roomdb.QuestionViewModel
import com.hnp.gplx600.theme.GPLXTheme

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
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
    setContent {
      GPLXTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
          MainApp(db = db, vm = viewModel)
        }
      }
    }
  }
}

@RequiresApi(Build.VERSION_CODES.Q)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainApp(db: AppDataBase, vm: QuestionViewModel) {
  val navController = rememberNavController()
  val globalVar = viewModel<GlobalVariable>()
  val lct = LocalContext.current
  GlobalFunction().initDatabase(lct, vm)
  NavHost(navController = navController, startDestination = "home") {
    composable("login") {
      Components().LoginScreen(navController = navController, globalVar = globalVar)
    }
    composable("home") {
      GplxHome().MyHome(navController = navController, globalVar = globalVar, vm = vm)
    }
    composable("detailscreen") {
      GplxComponents().DetailScreen(
        navController = navController, globalVar = globalVar, db = db, vm = vm
      )
    }
    composable("optionscreen") {
      GplxComponents().GPLXOptionScreen(navController = navController, globalVar = globalVar)
    }
    composable("examlistscreen") {
      GplxComponents().ExamListScreen(navController = navController, globalVar = globalVar, db = db, vm = vm)
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
