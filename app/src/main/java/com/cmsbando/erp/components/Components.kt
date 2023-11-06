package com.cmsbando.erp.components

import android.content.Context
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
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
import com.cmsbando.erp.MainActivity
import com.cmsbando.erp.R
import com.cmsbando.erp.api.ApiHandler
import com.cmsbando.erp.api.ErpInterface
import com.cmsbando.erp.api.GlobalVariable
import com.cmsbando.erp.theme.CMSVTheme
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Components {

  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  @Composable
  fun LoginScreen() {
    val globalVar = viewModel<GlobalVariable>()
    var username by remember {
      mutableStateOf("NHU1903")
    }
    var password by remember {
      mutableStateOf("123456789")
    }

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
          val ct: Context
          apiHandler.loginExcute(username, password, globalVar = globalVar)
//          globalVar.globalDialogState = true
        }, onSignUpClick = {
          val scope1 = GlobalScope.launch(Dispatchers.IO) {
            try {
              val apiHandler = ApiHandler()
              val result: JsonObject = apiHandler.generalQuery("checklogin", JsonObject())
              val tk_status: String = result.get("tk_status").asString
              if (tk_status != "ng") {
                val data: JsonObject = result.get("data").asJsonObject
                val myData = Gson().fromJson(data, ErpInterface.Employee::class.java)

                globalVar.showDialog("success",
                  "Thông báo",
                  "Đăng nhập thành công ${myData.MIDLAST_NAME} ${myData.FIRST_NAME}",
                  {
                    Log.d("xxx", "Day la hanh dong xay ra 1 ${data.get("EMPL_NO")}")
                  },
                  { Log.d("xxx", "Day la hanh dong xay ra 2 ${data.get("EMPL_NO")}") })
                println("Data xxx: ${myData.EMPL_NO}")
              } else {
                print("xxx: Có lỗi: ${result.get("message")}")
                globalVar.showDialog("error",
                  "Thông báo",
                  "Đăng nhập thất bại, kiểm tra lại tài khoản và mật khẩu",
                  { },
                  { })
              }

            } catch (e: HttpException) {
              Log.d("xxx", "Lỗi http")
              globalVar.showDialog("error",
                "Thông báo",
                "Đăng nhập thất bại, kiểm tra lại mạng mẹo",
                { },
                { })
            } catch (e: Exception) {
              globalVar.showDialog("error",
                "Thông báo",
                "Đăng nhập thất bại,${e.message.toString()}",
                { },
                { })
              //Log.d("xxx", "Lỗi khác: ${e.message.toString()}")
            }
          }
          Log.d("xxx", "Cai nay phai hien sau")
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

    }
  }
}