package com.hnp.gplx600.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.theme.CMSVTheme

class MyDialog {
  @Composable
  fun FNDialog(globalVar: GlobalVariable) {
//    val globalVar = viewModel<GlobalVariable>()
    MyAlertDialog(
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
  @Composable
  fun MyAlertDialog(
    dialogCat: String = "success",
    isShown: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
  ) {
    @Composable
    fun DialogIcon(dialogCat: String = "success") {
      val icon = if (dialogCat == "success") {
        Icons.Default.CheckCircle
      } else if (dialogCat == "error") {
        Icons.Default.Warning
      } else {
        Icons.Default.Info
      }
      val color = if (dialogCat == "success") {
        Color.Green
      } else if (dialogCat == "error") {
        Color.Red
      } else {
        Color.DarkGray
      }
      Icon(imageVector = icon, contentDescription = "success", tint = color)
    }
    if (isShown) {
      AlertDialog(icon = {
        DialogIcon(dialogCat = dialogCat)
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
          Text("Cancel")
        }
      })
    }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun NativeAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
  ) {
    AlertDialog(
      icon = {
        Icon(icon, contentDescription = "Example Icon")
      },
      title = {
        Text(text = dialogTitle)
      },
      text = {
        Text(text = dialogText)
      },
      onDismissRequest = {
        onDismissRequest()
      },
      confirmButton = {
        TextButton(
          onClick = {
            onConfirmation()
          }
        ) {
          Text("Confirm")
        }
      },
      dismissButton = {
        TextButton(
          onClick = {
            onDismissRequest()
          }
        ) {
          Text("Dismiss")
        }
      }
    )
  }

  @Composable
  fun CustomDialog(
    isShown: Boolean,
    type: String = "normal",
    dialogTitle: String,
    dialogText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
  ) {
    if (isShown) {
      Dialog(onDismissRequest = onDismiss) {
        Column(
          modifier = Modifier
            .padding(16.dp)
            .background(Color.Gray)
        ) {
          Text(text = dialogTitle, modifier = Modifier.size(12.dp))
          Text(text = dialogText)

          Button(onClick = { onConfirm() }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "OK")
          }
          if (type == "confirm") {
            Button(onClick = { onDismiss() }, modifier = Modifier.padding(top = 16.dp)) {
              Text(text = "Cancel")
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

    }
  }

}