package com.cmsbando.erp.components


import android.util.Log
import com.cmsbando.erp.api.ApiHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cmsbando.erp.R
import com.cmsbando.erp.api.GlobalVariable
import com.cmsbando.erp.theme.CMSVTheme

class Components {

  @Composable
  fun LoginScreen() {
    val globalVar = viewModel<GlobalVariable>()
    var username by remember {
      mutableStateOf("NHU1903")
    }
    var password by remember {
      mutableStateOf("123456789")
    }

    MyDialog().MyAlertDialog(isShown = globalVar.globalDialogState,
      onDismissRequest = { globalVar.globalDialogState = false },
      onConfirmation = {
        globalVar.globalDialogState = false
        Log.d("xxx", "Đã bấm OK") // Add logic here to handle confirmation.
      },
      dialogTitle = "Alert dialog example ",
      dialogText = "This is an example of an alert dialog with buttons.",
      icon = Icons.Default.Info
    )

    Box(modifier = Modifier.fillMaxSize()) {
      Image(
        painter = painterResource(id = R.drawable.cmsv_background),
        contentDescription = "CMS Logo",
        modifier = Modifier
          .fillMaxSize()
          .blur(6.dp),
        contentScale = ContentScale.Crop
      )
    }

    Box(
      modifier = Modifier
        .fillMaxSize()
        .alpha(0.6f)
        .background(MaterialTheme.colorScheme.background)
        .padding(10.dp)
        .clip(
          CutCornerShape(
            topStart = 8.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 8.dp
          )
        )
    ) {

      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
      ) {
        LoginHeader()
        LoginField(username, password, onUserNameChange = {
          username = it
        }, onPasswordChange = {
          password = it
        })
        Text(text = "Gia tri la: ${globalVar.globalDialogState.toString()}")
        LoginFooter(onSignInClick = {
          val apiHandler = ApiHandler()
          apiHandler.loginExcute(username, password)
          globalVar.globalDialogState = true
        }, onSignUpClick = {

        })
      }
    }

  }

  @Composable
  fun LoginHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = "Welcome Back",
        fontSize = 36.sp,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center
      )
      Text(text = "Login to use ERP", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
    }

  }

  @Composable
  fun LoginField(
    username: String,
    password: String,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
  ) {
    Column {
      CustomField(value = username,
        label = "Username",
        placeholder = "Enter your ID",
        onValueChange = onUserNameChange,
        leadingIcon = {
          Icon(Icons.Default.AccountBox, contentDescription = "ERP ID")
        })
      Spacer(modifier = Modifier.height(8.dp))
      CustomField(
        value = password,
        label = "Password",
        placeholder = "Enter your password",
        onValueChange = onPasswordChange,
        leadingIcon = {
          Icon(Icons.Default.Lock, contentDescription = "ERP PASSWORD")
        },
        visualTransformation = PasswordVisualTransformation()
      )
      TextButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.End)) {
        Text(text = "Forgot password")
      }

    }

  }

  @Composable
  fun LoginFooter(
    onSignInClick: () -> Unit, onSignUpClick: () -> Unit
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Button(onClick = onSignInClick, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Login")
      }
      TextButton(onClick = onSignUpClick) {
        Text(text = "Don't have an account, click here")
      }

    }


  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun CustomField(
    value: String,
    label: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
  ) {
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      label = {
        Text(text = label)
      },
      placeholder = {
        Text(text = placeholder)
      },
      singleLine = true,
      visualTransformation = visualTransformation,
      keyboardOptions = keyboardOptions,
      leadingIcon = leadingIcon,
      trailingIcon = trailingIcon,
    )
  }

  @Preview(showBackground = true, showSystemUi = true)
  @Composable
  fun GreetingPreview() {
    CMSVTheme {
      LoginScreen()
    }
  }
}