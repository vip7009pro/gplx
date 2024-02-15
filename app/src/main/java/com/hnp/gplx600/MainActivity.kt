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
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.components.Components
import com.hnp.gplx600.pages.Home
import com.hnp.gplx600.pages.nhansu.DiemDanhNhom
import com.hnp.gplx600.theme.CMSVTheme

class MainActivity : ComponentActivity() {

  @RequiresApi(Build.VERSION_CODES.Q)
  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CMSVTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
          MainApp()
        }
      }
    }
  }
}


@RequiresApi(Build.VERSION_CODES.Q)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainApp() {
  val navController = rememberNavController()
  val globalVar = viewModel<GlobalVariable>()
  NavHost(navController = navController, startDestination = "home") {
    composable("login") {
      Components().LoginScreen(navController = navController, globalVar = globalVar)
    }
    composable("home") {
      Home().MyHome(navController = navController, globalVar = globalVar)
    }
    composable("diemdanhnhom") {
      DiemDanhNhom().DiemDanhNhomScreen(navController = navController, globalVar = globalVar)
    }
  }

}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
  CMSVTheme {

  }
}
