package com.cmsbando.erp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cmsbando.erp.api.GlobalVariable
import com.cmsbando.erp.components.Components
import com.cmsbando.erp.components.MyDialog
import com.cmsbando.erp.theme.CMSVTheme

class MainActivity : ComponentActivity() {
  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val ct: Context = applicationContext
    setContent {
      CMSVTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
          val globalVar = viewModel<GlobalVariable>()
          Components().LoginScreen()
          MyDialog().MyAlertDialog(isShown = globalVar.globalDialogState,
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
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
  CMSVTheme {
    Components().LoginScreen()
  }
}
