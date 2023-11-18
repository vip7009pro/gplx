package com.cmsbando.erp.pages.nhansu

import android.content.Context
import android.net.http.HttpException
import android.os.Build
import android.os.ext.SdkExtensions
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cmsbando.erp.api.ApiHandler
import com.cmsbando.erp.api.ErpInterface
import com.cmsbando.erp.api.GlobalVariable
import com.cmsbando.erp.api.LocalData
import com.cmsbando.erp.components.MyDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiemDanhNhom {

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun DiemDanhNhomScreen(navController: NavController, globalVar: GlobalVariable) {
    val Boxct: Context = LocalContext.current
    var listDiemDanh by remember { mutableStateOf<List<ErpInterface.DiemDanhNhomData>>(emptyList()) }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun loadDiemDanhNhom() {
      val scopeCheckLogin = GlobalScope.launch(Dispatchers.Main) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && SdkExtensions.getExtensionVersion(
            Build.VERSION_CODES.S
          ) >= 7
        ) {
          try {
            val savedToken: String = LocalData().getData(Boxct, "token")
            val apiHandler = ApiHandler(globalVar)
            var apiData = JsonObject()
            apiData.addProperty("team_name_list", 5)
            val result: JsonObject = apiHandler.generalQuery("diemdanhnhom", apiData, savedToken)
            val tk_status: String = result.get("tk_status").asString

            if (tk_status != "ng") {
              val data = result.get("data").asJsonArray
              val DiemDanhNhomType =
                object : TypeToken<List<ErpInterface.DiemDanhNhomData>>() {}.type
              val dataList: List<ErpInterface.DiemDanhNhomData> =
                Gson().fromJson(data, DiemDanhNhomType)
              listDiemDanh = dataList
              globalVar.showDialog("success",
                "Thông báo",
                "Load thành công ${dataList.size} dòng",
                {},
                {})
            } else {
              globalVar.showDialog("error",
                "Thông báo",
                "Có lỗi: ${result.get("message").asString}",
                { },
                { })
            }
          } catch (e: HttpException) {
            Log.d("xxx", "Lỗi http")
            globalVar.showDialog("error",
              "Thông báo",
              "Tải data thất bại, kiểm tra lại mạng mẹo",
              { },
              { })
          } catch (e: Exception) {
            globalVar.showDialog("error",
              "Thông báo",
              "Tải data thất bại: ,${e.message.toString()}",
              { },
              { })
          }
        }
      }
    }
    LaunchedEffect(true) {
      loadDiemDanhNhom()
    }

    Scaffold(
      topBar = {
        TopAppBar(modifier = Modifier.height(40.dp),
          colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
          ),
          title = {})

      },
      bottomBar = {
        BottomAppBar(
          modifier = Modifier.height(40.dp),
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          contentColor = MaterialTheme.colorScheme.primary,
        ) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Bottom app bar",
          )
        }
      },
      floatingActionButton = {
        /*FloatingActionButton(onClick = {

        }, modifier = Modifier.size(40.dp)) {
          Icon(Icons.Default.Menu, contentDescription = "Menu", modifier = Modifier.size(30.dp))
        }*/
      },
      floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
      Column(modifier = Modifier.padding(top = 40.dp)) {
        Text(text = "Điểm danh nhóm screen")
        LazyColumn() {
          this.items(listDiemDanh) { ele ->
            Text(text = ele.MIDLAST_NAME + " " + ele.FIRST_NAME)
          }
        }
        paddingValues
      }

    }

    MyDialog().FNDialog(globalVar = globalVar)
  }

}