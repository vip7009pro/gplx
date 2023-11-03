package com.cmsbando.erp.components


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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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

class MyDialog {
  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun RetroDialog(
    alertType: String,
    title: String,
    message: String,
    titleBackGroundColor: Color = Color(0xFFCCCCCC),
    backgroundColor: Color = Color(0xFFCCCCCC),
    onDismissRequest: () -> Unit
  ) {

    Dialog(onDismissRequest = onDismissRequest) {
      Box(
        Modifier
          .clip(RectangleShape)
          .fillMaxWidth()
          .background(backgroundColor)
      ) {
        Column {
          Row(
            Modifier
              .fillMaxWidth()
              .background(titleBackGroundColor)
              .padding(start = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(title, color = Color.White, fontFamily = FontFamily.Default)
            Surface(
              onClick = onDismissRequest, shape = RectangleShape,
              modifier = Modifier.padding(2.dp),
              color = backgroundColor,
            ) {
              Icon(Icons.Default.Close, contentDescription = "Close")
            }
          }

          Row(
            modifier = Modifier.padding(
              horizontal = 16.dp, vertical = 20.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {

            if (alertType == "error") {
              Icon(Icons.Default.Close, contentDescription = "Close")
            } else if (alertType == "success") {
              Icon(Icons.Filled.CheckCircle, contentDescription = "Thành công", tint = Color.Green)
            }
            Text(message, fontFamily = FontFamily.Monospace)
          }
          Surface(
            modifier = Modifier
              .align(Alignment.CenterHorizontally)
              .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(5.dp)),
            onClick = onDismissRequest,
            shape = RectangleShape,
            color = backgroundColor,
          ) {
            Text(
              "OK",
              fontFamily = FontFamily.Monospace,
              modifier = Modifier
                .widthIn(100.dp)
                .padding(vertical = 6.dp),
              textAlign = TextAlign.Center
            )
          }
        }
      }
    }
  }

  @Composable
  fun MyAlertDialog(
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

  @Preview(showBackground = true, showSystemUi = true)
  @Composable
  fun GreetingPreview() {
    CMSVTheme {

    }
  }

}