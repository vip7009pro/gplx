package com.cmsbando.erp.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cmsbando.erp.theme.CMSVTheme
import io.github.farhanroy.composeawesomedialog.ComposeAwesomeDialog
import io.github.farhanroy.composeawesomedialog.utils.ComposeAwesomeDialogType

class MyDialog {

  var showDialog by mutableStateOf(false)


  @Composable
  fun MyAlertDialog(
    isShown: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
  ) {
    if(isShown) {
      AlertDialog(icon = {
        Icon(icon, contentDescription = "Example Icon")
      }, title = {
        Text(text = dialogTitle)
      }, text = {
        Text(text = dialogText)
      }, onDismissRequest = {
        onDismissRequest()
      }, confirmButton = {
        TextButton(onClick = {
          onConfirmation()
        }) {
          Text("Confirm")
        }
      }, dismissButton = {
        TextButton(onClick = {
          onDismissRequest()
        }) {
          Text("Dismiss")
        }
      })
    }
  }

  /*  @Composable
    fun showAwesomeDialog() {
      val openDialog = remember { mutableStateOf(true) }
      if (openDialog.value) {
        ComposeAwesomeDialog(type = ComposeAwesomeDialogType.Success,
          title = "Success",
          desc = "This is success dialog",
          onDismiss = { openDialog.value = false })
      }
    }*/


  @Preview(showBackground = true, showSystemUi = true)
  @Composable
  fun GreetingPreview() {
    CMSVTheme {

    }
  }

}