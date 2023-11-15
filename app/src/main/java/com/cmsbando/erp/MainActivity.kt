package com.cmsbando.erp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cmsbando.erp.api.GlobalVariable
import com.cmsbando.erp.components.Components
import com.cmsbando.erp.components.MyDialog
import com.cmsbando.erp.pages.Home
import com.cmsbando.erp.theme.CMSVTheme

class MainActivity : ComponentActivity() {
  @RequiresApi(Build.VERSION_CODES.O)
  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CMSVTheme {
        val globalVar = viewModel<GlobalVariable>()
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {

//          Home().MyHome()

          MainApp()

//          TestApp()

        }

        MyDialog().MyAlertDialog(
          isShown = globalVar.globalDialogState,
          onDismissRequest = {
            globalVar.onDialogCancel?.invoke()
            globalVar.globalDialogState = false
          },
          onConfirmation = {
            globalVar.onDialogConfirm?.invoke()
            globalVar.globalDialogState = false
          },
          dialogTitle = globalVar.globalDialogTitle,
          dialogText = globalVar.globalDialogText,
          dialogCat = globalVar.globalDialogCat,
        )
      }
    }
  }
}

@Composable
fun TestApp() {
  Surface {
    val globalVar = viewModel<GlobalVariable>()
    Button(onClick = { globalVar.globalDialogState = !globalVar.globalDialogState }) {
      Text(text = "Click to change")
    }
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainApp() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = "login") {
    composable("login") {
      Components().LoginScreen()
    }
    composable("home") {
      Home().MyHome()
    }
  }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
  CMSVTheme {
    Components().LoginScreen()
  }
}
