package com.cmsbando.erp.components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.cmsbando.erp.theme.CMSVTheme

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
    if (isShown) {
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

  @Preview(showBackground = true, showSystemUi = true)
  @Composable
  fun GreetingPreview() {
    CMSVTheme {

    }
  }

}