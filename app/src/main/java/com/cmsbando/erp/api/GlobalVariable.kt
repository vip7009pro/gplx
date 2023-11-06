package com.cmsbando.erp.api

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GlobalVariable() : ViewModel() {
  var globalDialogState: Boolean by mutableStateOf(false)
  var globalDialogTitle: String by mutableStateOf("")
  var globalDialogText: String by mutableStateOf("")
  var globalDialogType: String by mutableStateOf("normal")
  var globalDialogCat: String by mutableStateOf("success")
  var onDialogConfirm: (() -> Unit)? = null
  var onDialogCancel: (() -> Unit)? = null

  fun showDialog(dialogCat: String = "success", dialogTitle: String = "Title", dialogText: String = "Text", dlConfirm: (()-> Unit),dlCancel: (()-> Unit)) {
    globalDialogState = true
    globalDialogCat = dialogCat
    globalDialogTitle= dialogTitle
    globalDialogText = dialogText
    onDialogConfirm = dlConfirm
    onDialogCancel = dlCancel
  }
}