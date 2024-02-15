package com.hnp.gplx600.components

import android.content.Context
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.hnp.gplx600.R
import com.hnp.gplx600.api.ApiHandler
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.api.LocalData
import com.hnp.gplx600.theme.CMSVTheme
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Components {
  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  @Composable
  fun LoginScreen(navController: NavController, globalVar: GlobalVariable) {
    val boxCt: Context = LocalContext.current
    val savedUser: String = LocalData().getData(boxCt, "user")
    val savedPass: String = LocalData().getData(boxCt, "pass")
    val savedServer: String = LocalData().getData(boxCt, "server")
    if (savedServer !== "") {
      globalVar.currentServer = savedServer
    }
    var username by remember {
      mutableStateOf(savedUser)
    }
    var password by remember {
      mutableStateOf(savedPass)
    }

    fun loginfunc() {
      val scopeLogin = GlobalScope.launch(Dispatchers.Main) {
        try {
          val apiHandler = ApiHandler(globalVar)
          val result: JsonObject = apiHandler.login(username, password)
          if (result != null) {
            val tk_status: String = result.get("tk_status").asString
            if (tk_status != "ng") {
              val empl_info = result.get("userData").asString
              val gson = Gson()
              val employeeType = object : TypeToken<List<ErpInterface.Employee>>() {}.type
              val persons: List<ErpInterface.Employee> = gson.fromJson(empl_info, employeeType)
              globalVar.token = result.get("token_content").asString
              LocalData().saveData(boxCt, "token", result.get("token_content").asString)
              globalVar.userData = persons[0]
              //save local account
              LocalData().saveData(boxCt, "user", username)
              LocalData().saveData(boxCt, "pass", password)
//              Log.d("xxx", "Đăng nhập thành công")
              navController.navigate("home") {
                popUpTo("home") {
                  inclusive = true
                }
              }/*globalVar.showDialog("success",
                "Thông báo",
                "Đăng nhập thành công, xin chào:  ${persons.get(0).MIDLAST_NAME} ${persons.get(0).FIRST_NAME}",
                {
                  Log.d("xxx", "Day la hanh dong xay ra 1 ${persons.get(0).FIRST_NAME}")
                },
                {
                  Log.d("xxx", "Day la hanh dong xay ra 2 ${persons.get(0).FIRST_NAME}")
                })*/
            } else {
              globalVar.showDialog("error",
                "Thông báo",
                "Đăng nhập thất bại, kiểm tra lại tài khoản và mật khẩu",
                {},
                {})
            }
          } else {
            globalVar.showDialog("error",
              "Thông báo",
              "Đăng nhập thất bại, kiểm tra lại tài khoản và mật khẩu",
              {},
              {})
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
    }

    fun checkLogin() {
      val scopeCheckLogin = GlobalScope.launch(Dispatchers.Main) {
        try {
          val savedToken: String = LocalData().getData(boxCt, "token")
          val apiHandler = ApiHandler(globalVar)
          val result: JsonObject = apiHandler.generalQuery("checklogin", JsonObject(), savedToken)
          val tk_status: String = result.get("tk_status").asString
          if (tk_status != "ng") {
            val data: JsonObject = result.get("data").asJsonObject
            val myData = Gson().fromJson(data, ErpInterface.Employee::class.java)
            globalVar.userData = myData
            navController.navigate("home") {
              popUpTo("home") {
                saveState = true
              }
              launchSingleTop = true
              restoreState = true
            }/*globalVar.showDialog("success",
                "Thông báo",
                "Đăng nhập thành công ${myData.MIDLAST_NAME} ${myData.FIRST_NAME}",
                {
                  Log.d("xxx", "Day la hanh dong xay ra 1 ${data.get("EMPL_NO")}")
                },
                { Log.d("xxx", "Day la hanh dong xay ra 2 ${data.get("EMPL_NO")}") })
              */
          } else {
            print("xxx: Có lỗi: ${result.get("message")}")/* globalVar.showDialog("error",
               "Thông báo",
               "Đăng nhập thất bại, kiểm tra lại tài khoản và mật khẩu",
               { },
               { })*/
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
    }

    LaunchedEffect(true) {
      checkLogin()
    }
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
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
        verticalArrangement = Arrangement.Center
      ) {
        LoginHeader()
        Spacer(modifier = Modifier.height(40.dp))

        LoginField(username,
          password,
          onUserNameChange = { username = it },
          onPasswordChange = { password = it })
        Spacer(modifier = Modifier.height(20.dp))
        LoginFooter(onSignInClick = { loginfunc() }, onSignUpClick = {

        })
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Chọn server:", fontSize = 15.sp)
          ServerSelectMenu(globalVar = globalVar)
        }
        MyDialog().FNDialog(globalVar = globalVar)
      }
    }
  }

  @Composable
  fun LoginHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        painter = painterResource(id = R.drawable.logocmsvina),
        contentDescription = "CMS Logo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .width(280.dp)
          .height(50.dp)
          .fillMaxWidth()
          .fillMaxHeight()
      )
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
    }

  }

  @OptIn(ExperimentalComposeUiApi::class)
  @Composable
  fun ServerSelectMenu(globalVar: GlobalVariable) {
    val Boxct: Context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    // Example of using KeyboardOptions and KeyboardActions
    val keyboardOptions = KeyboardOptions.Default.copy(
      keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
      modifier = Modifier
        .padding(5.dp)
        .background(color = Color.White),
      contentAlignment = Alignment.CenterStart,
    ) {
      // Clickable text to show dropdown
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = globalVar.currentServer,
          modifier = Modifier
            .padding(16.dp)
            .clickable { expanded = true },
          color = Color.Black,
          fontSize = 13.sp
        )
        Spacer(modifier = Modifier.width(2.dp))
        FaIcon(faIcon = FaIcons.Desktop, tint = Color("#79AAFA".toColorInt()))
      }

      // Dropdown menu
      DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
      ) {
        // Dropdown menu items
        DropdownMenuItem(text = {
          Text(text = "MAIN_SERVER", fontSize = 12.sp)
        }, onClick = {
          globalVar.currentServer = "MAIN_SERVER"
          LocalData().saveData(Boxct, "server", "MAIN_SERVER")
          expanded = false
        }, leadingIcon = { FaIcon(faIcon = FaIcons.Desktop, tint = Color("#79AAFA".toColorInt())) })
        DropdownMenuItem(text = {
          Text(text = "SUB_SERVER", fontSize = 12.sp)
        }, onClick = {
          globalVar.currentServer = "SUB_SERVER"
          LocalData().saveData(Boxct, "server", "SUB_SERVER")
          expanded = false
        }, leadingIcon = { FaIcon(faIcon = FaIcons.Desktop, tint = Color("#79AAFA".toColorInt())) })

      }
    }
  }

  @OptIn(ExperimentalComposeUiApi::class)
  @Composable
  fun LoginFooter(
    onSignInClick: () -> Unit, onSignUpClick: () -> Unit
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Button(
        onClick = onSignInClick,
        modifier = Modifier
          .padding(16.dp)
          .height(50.dp)
          .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color("#357AEA".toColorInt()), contentColor = Color.White
        )
      ) {
        Text(text = "Login")
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