package com.hnp.gplx600

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.hnp.gplx600.api.AppDataBase
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.components.Components
import com.hnp.gplx600.pages.gplxhome.GplxComponents
import com.hnp.gplx600.pages.gplxhome.GplxHome
import com.hnp.gplx600.theme.GPLXTheme

class MainActivity : ComponentActivity() {

  private lateinit var db: AppDataBase
  @RequiresApi(Build.VERSION_CODES.Q)
  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    db = Room.databaseBuilder(applicationContext, AppDataBase::class.java,"gplx_database").build()
    setContent {
      GPLXTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
          MainApp(db = db)
        }
      }
    }
  }
}
@RequiresApi(Build.VERSION_CODES.Q)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainApp(db: AppDataBase) {
  val navController = rememberNavController()
  val globalVar = viewModel<GlobalVariable>()
  NavHost(navController = navController, startDestination = "home") {
    composable("login") {
      Components().LoginScreen(navController = navController, globalVar = globalVar)
    }
    composable("home") {
      GplxHome().MyHome(navController = navController, globalVar = globalVar)
    }
    composable("detailscreen") {
      GplxComponents().DetailScreen(navController = navController, globalVar = globalVar, db = db)
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
