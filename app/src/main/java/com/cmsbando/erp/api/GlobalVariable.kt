package com.cmsbando.erp.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GlobalVariable() : ViewModel() {
  var globalDialogState: Boolean by mutableStateOf(false)
}