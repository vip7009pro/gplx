package com.hnp.gplx600.pages.nhansu

import android.content.Context
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hnp.gplx600.api.ApiHandler
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.api.LocalData
import com.hnp.gplx600.components.MyDialog
import com.hnp.gplx600.theme.CMSVTheme
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiemDanhNhom {
  @OptIn(ExperimentalMaterial3Api::class)
  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  @Composable
  fun DiemDanhNhomScreen(navController: NavController, globalVar: GlobalVariable) {
    val Boxct: Context = LocalContext.current
    var listDiemDanh by remember { mutableStateOf<List<ErpInterface.DiemDanhNhomData>>(emptyList()) }
    fun loadDiemDanhNhom() {
      val scopeCheckLogin = GlobalScope.launch(Dispatchers.IO) {
        try {
          val savedToken: String = LocalData().getData(Boxct, "token")
          val apiHandler = ApiHandler(globalVar)
          var apiData = JsonObject()
          apiData.addProperty("team_name_list", 5)
          val result: JsonObject = apiHandler.generalQuery("diemdanhnhom", apiData, savedToken)
          val tk_status: String = result.get("tk_status").asString

          if (tk_status != "ng") {
            val data = result.get("data").asJsonArray
            val DiemDanhNhomType = object : TypeToken<List<ErpInterface.DiemDanhNhomData>>() {}.type
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
          title = {
            Text("Điểm danh nhóm")
          })
      },
      bottomBar = {
        /*BottomAppBar(
          modifier = Modifier.height(40.dp),
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          contentColor = MaterialTheme.colorScheme.primary,
        ) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Bottom app bar",
          )
        }*/
      },
      floatingActionButton = {
        /*FloatingActionButton(onClick = {

        }, modifier = Modifier.size(40.dp)) {
          Icon(Icons.Default.Menu, contentDescription = "Menu", modifier = Modifier.size(30.dp))
        }*/
      },
      floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp)
            .fillMaxSize(),
          verticalArrangement = Arrangement.Top
        ) {
          this.itemsIndexed(listDiemDanh) { index, ele ->
            DiemDanhNhomElement(diemdanhDataRow = ele, index = index)
          }
        }
      paddingValues

    }
    MyDialog().FNDialog(globalVar = globalVar)
  }
  @Composable
  fun DiemDanhNhomElement(diemdanhDataRow: ErpInterface.DiemDanhNhomData, index: Int) {
    val ROW_HEIGHT = 60.dp
    val TEXT_BUTTON_MARGIN = 15.dp
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .background(
          brush = Brush.verticalGradient(
            listOf(
              Color("#BEEDA6".toColorInt()), Color("#A3CFF7".toColorInt())
            )
          )
        )
        .height(ROW_HEIGHT)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 5.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)
          .height(ROW_HEIGHT),
      ) {
        Column {
          AsyncImage(
            model = "http://14.160.33.94/Picture_NS/NS_${diemdanhDataRow.EMPL_NO}.jpg",
            contentDescription = "employee image",
            modifier = Modifier
              .width(50.dp)
              .height(50.dp)
              .clip(
                RoundedCornerShape(50.dp)
              ),
            contentScale = ContentScale.FillBounds
          )
        }
        Column {
          Row(modifier = Modifier.fillMaxWidth()) {
            Text(
              text = "${(index + 1).toString()}. ${diemdanhDataRow.MIDLAST_NAME} ${diemdanhDataRow.FIRST_NAME}/${diemdanhDataRow.EMPL_NO} / ${diemdanhDataRow.CMS_ID} ",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
          if(diemdanhDataRow.ON_OFF == 1)
          {
            Text(text = "Đi làm",
              fontSize = 13.sp,
              color = Color("#359204".toColorInt()),
              modifier = Modifier
                .clickable { }
                .padding(end = TEXT_BUTTON_MARGIN),
              fontStyle = FontStyle.Italic)
          }
          else if(diemdanhDataRow.ON_OFF == 0)
          {
            Text(text = "Nghỉ làm",
              fontSize = 13.sp,
              color = Color("#FF0000".toColorInt()),
              modifier = Modifier
                .clickable { }
                .padding(end = TEXT_BUTTON_MARGIN),
              fontStyle = FontStyle.Italic)
          }
          else {
            Row(modifier = Modifier.fillMaxWidth()) {
              Text(text = "Làm ngày",
                fontSize = 13.sp,
                color = Color("#359204".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
              Text(text = "Làm đêm",
                fontSize = 13.sp,
                color = Color("#6D6959".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
              Text(text = "Nghỉ 50%",
                fontSize = 13.sp,
                color = Color("#CA9D04".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
              Text(text = "Nghỉ làm",
                fontSize = 13.sp,
                color = Color("#FF0000".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
            }
          }
          if(diemdanhDataRow.OVERTIME == 1 || diemdanhDataRow.OVERTIME == 0)
          {
            diemdanhDataRow.OVERTIME_INFO?.let {
              Text(text = diemdanhDataRow.OVERTIME_INFO ,
                fontSize = 13.sp,
                color = Color("#FF0000".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
            }

          }
          else
          {
            Row(modifier = Modifier.fillMaxWidth()) {
              Text(text = "KTC",
                fontSize = 13.sp,
                color = Color("#359204".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
              Text(text = "17-20",
                fontSize = 13.sp,
                color = Color("#6D6959".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
              Text(text = "02-06",
                fontSize = 13.sp,
                color = Color("#CA9D04".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
              Text(text = "17-18",
                fontSize = 13.sp,
                color = Color("#FF0000".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
              Text(text = "05-08",
                fontSize = 13.sp,
                color = Color("#FF0000".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
              Text(text = "16-20",
                fontSize = 13.sp,
                color = Color("#FF0000".toColorInt()),
                modifier = Modifier
                  .clickable { }
                  .padding(end = TEXT_BUTTON_MARGIN),
                fontStyle = FontStyle.Italic)
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