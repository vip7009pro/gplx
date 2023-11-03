package com.cmsbando.erp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.cmsbando.erp.components.Components
import com.cmsbando.erp.components.MyDialog
import com.cmsbando.erp.theme.CMSVTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CMSVTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
          val openAlertDialog = remember { mutableStateOf(true) }
          Components().LoginScreen()

          when {
            openAlertDialog.value -> {
              MyDialog().MyAlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                  openAlertDialog.value = false
                  Log.d("xxx", "Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Alert dialog example ",
                dialogText = "This is an example of an alert dialog with buttons.",
                icon = Icons.Default.Info
              )
            }
          }
        }
      }
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
  CMSVTheme {
    Components().LoginScreen()
  }
}
