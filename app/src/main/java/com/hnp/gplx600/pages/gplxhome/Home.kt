package com.hnp.gplx600.pages.gplxhome

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.hnp.gplx600.R
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.components.MyDialog
import com.hnp.gplx600.components.NavigationDrawerMenu
import com.hnp.gplx600.theme.GPLXTheme
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.launch
import androidx.compose.ui.text.*;
import androidx.compose.ui.text.font.FontWeight

@Suppress("UNUSED_EXPRESSION")
class GplxHome {
  @RequiresApi(Build.VERSION_CODES.O)
  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun MyHome(navController: NavController, globalVar: GlobalVariable) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    //val globalVar = viewModel<GlobalVariable>()
    val currentContext = LocalContext.current
    val menuData = listOf(
      ErpInterface.MenuData(route = "nhansubophan",
        title = "Nhân sự bộ phận",
        icon = {
          Icon(
            Icons.Default.Person,
            contentDescription = "Nhansubophan",
            modifier = Modifier.size(30.dp),
            tint = Color.Red
          )
        },
        subMenu = listOf(
          ErpInterface.SubMenuData(route = "diemdanhnhom", title = "Điểm danh nhóm", icon = {
            Spacer(modifier = Modifier.width(5.dp))
            FaIcon(faIcon = FaIcons.Check, size = 20.dp, tint = Color.Green)
          }, onClick = {
            /*navController.navigate("diemdanhnhom") {
              popUpTo("diemdanhnhom") {
                inclusive = true
              }
            }*/
          }),
          ErpInterface.SubMenuData(route = "dieuchuyenteam", title = "Điều chuyển team", icon = {
            Icon(
              Icons.Default.Refresh,
              contentDescription = "dieuchuyenteam",
              modifier = Modifier.size(25.dp),
              tint = Color("#F87D23".toColorInt())
            )
          }, onClick = { Log.d("xxx", "click 2") }),
          ErpInterface.SubMenuData(route = "dangky", title = "Đăng ký", icon = {
            Icon(
              Icons.Default.Create,
              contentDescription = "dangky",
              modifier = Modifier.size(25.dp),
              tint = Color("#A10AF8".toColorInt())
            )
          }, onClick = { Log.d("xxx", "click 3") }),
          ErpInterface.SubMenuData(route = "pheduyetnghi", title = "Phê duyệt nghỉ", icon = {
            Icon(
              Icons.Outlined.CheckCircle,
              contentDescription = "pheduyetnghi",
              modifier = Modifier.size(25.dp),
              tint = Color("#16B0FC".toColorInt())
            )
          }, onClick = { Log.d("xxx", "click 3") }),
          ErpInterface.SubMenuData(route = "lichsudilam", title = "Lịch sử đi làm", icon = {
            Icon(
              Icons.Outlined.DateRange,
              contentDescription = "lichsudilam",
              modifier = Modifier.size(25.dp),
              tint = Color("#FD44BC".toColorInt())
            )
          }, onClick = { Log.d("xxx", "click 3") }),
          ErpInterface.SubMenuData(route = "quanlycapcao", title = "Quản lý cấp cao", icon = {
            Icon(
              Icons.Outlined.Person,
              contentDescription = "quanlycapcao",
              modifier = Modifier.size(25.dp),
              tint = Color("#07C94D".toColorInt())
            )
          }, onClick = { Log.d("xxx", "click 3") }),
          ErpInterface.SubMenuData(route = "baocaonhansu", title = "Báo cáo nhân sự", icon = {
            Spacer(modifier = Modifier.width(5.dp))
            FaIcon(faIcon = FaIcons.ChartLine, size = 20.dp, tint = Color("#E6D104".toColorInt()))
          }, onClick = { Log.d("xxx", "click 3") }),
          ErpInterface.SubMenuData(route = "quanlyphongbannhansu",
            title = "Quản lý phòng ban - nhân sự",
            icon = {
              Spacer(modifier = Modifier.width(5.dp))
              FaIcon(faIcon = FaIcons.Male, size = 20.dp, tint = Color("#00BFB7".toColorInt()))
            },
            onClick = { Log.d("xxx", "click 3") }),

          )
      ),
      ErpInterface.MenuData(
        route = "nhansubophan",
        title = "Nhân sự bộ phận",
        icon = { FaIcon(faIcon = FaIcons.Home, size = 30.dp, tint = Color.Gray) },
        subMenu = listOf(
          ErpInterface.SubMenuData(route = "nhansubophan",
            title = "Nhân sự bộ phận",
            icon = { FaIcon(faIcon = FaIcons.Home, size = 24.dp, tint = Color.Gray) },
            onClick = { Log.d("xxx", "click 1") }),
          ErpInterface.SubMenuData(route = "nhansubophan",
            title = "Nhân sự bộ phận",
            icon = { FaIcon(faIcon = FaIcons.Home, size = 24.dp, tint = Color.Gray) },
            onClick = { Log.d("xxx", "click 2") }),
          ErpInterface.SubMenuData(route = "nhansubophan",
            title = "Nhân sự bộ phận",
            icon = { FaIcon(faIcon = FaIcons.Home, size = 24.dp, tint = Color.Gray) },
            onClick = { Log.d("xxx", "click 3") }),
        )
      ),
    )
    val isDarkMode = isSystemInDarkTheme()
    ModalNavigationDrawer(
      drawerState = drawerState,
      drawerContent = {
        ModalDrawerSheet {
          Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Box(
              modifier = Modifier
                .size(300.dp, 40.dp) // Set your desired width and height
                .clip(RoundedCornerShape(16.dp))
            ) {
              Image(
                painter = painterResource(id = R.drawable.gplx_600_dai),
                contentDescription = null,
                modifier = Modifier
                  .fillMaxSize()
                  .clip(RoundedCornerShape(16.dp))
                  .size(300.dp, 50.dp), // Set the same width and height as the Box
                contentScale = ContentScale.Crop
              )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            NavigationDrawerMenu().NavMenu(
              items = menuData, scaffoldState = drawerState, coroutineScope = scope
            )
          }
        }
      },
    ) {
      Scaffold(
        topBar = {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .background(color = Color(0xFFE5EDF7)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "Chọn bằng lái", color = Color(0xFF5DBACA), fontWeight = FontWeight.Bold)
          }
        },
        floatingActionButton = {
          FloatingActionButton(onClick = {
            scope.launch {
              drawerState.apply {
                if (isClosed) open() else close()
              }
            }
          }, modifier = Modifier.size(40.dp)) {
            Icon(Icons.Default.Menu, contentDescription = "Menu", modifier = Modifier.size(30.dp))
          }
        },
//        floatingActionButtonPosition = FabPosition.End,
      ) { paddingValues ->

        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
          verticalArrangement = Arrangement.SpaceEvenly,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier
              .height(140.dp)
              .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            GplxComponents().HomeCard("A1",
              "200 câu",
              Color(0xFFB7D7F1),
              Color(0xFF40DA6B),
              R.drawable.a1,
              onClick = {
//                navController.navigate("detailscreen") {}
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("A1")
              })
            GplxComponents().HomeCard("A2",
              "450 câu",
              Color(0xFFB7D7F1),
              Color(0xFF416AFF),
              R.drawable.a2,
              onClick = {
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("A2")
              })
          }
          Row(
            modifier = Modifier
              .height(140.dp)
              .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            GplxComponents().HomeCard("A3",
              "500 câu",
              Color(0xFFF7CB44),
              Color(0xFFF9FFFB),
              R.drawable.a3,
              onClick = {
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("A3")
              })
            GplxComponents().HomeCard("A4",
              "500 câu",
              Color(0xFFFFFFFF),
              Color(0xFFFD4848),
              R.drawable.a4,
              onClick = {
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("A4")
              })
          }
          Row(
            modifier = Modifier
              .height(140.dp)
              .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            GplxComponents().HomeCard("B1",
              "574 câu",
              Color(0xFFF2F9FF),
              Color(0xFF00BCD4),
              R.drawable.b1,
              onClick = {
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("B1")

              })
            GplxComponents().HomeCard("B2",
              "600 câu",
              Color(0xFFC1FFEC),
              Color(0xFFFFEB3B),
              R.drawable.b22,
              onClick = {
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("B2")
              })
          }
          Row(
            modifier = Modifier
              .height(140.dp)
              .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            GplxComponents().HomeCard("C",
              "600 câu",
              Color(0xFFE76CA5),
              Color(0xFFC2C7C4),
              R.drawable.c,
              onClick = {
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("C")
              })
            GplxComponents().HomeCard("D",
              "600 câu",
              Color(0xFFFFB15E),
              Color(0xFFFEFFFE),
              R.drawable.d,
              onClick = {
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("D")
              })
          }
          Row(
            modifier = Modifier
              .height(140.dp)
              .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            GplxComponents().HomeCard("E",
              "600 câu",
              Color(0xFF2341FF),
              Color(0xFFE9E9E9),
              R.drawable.e,
              onClick = {
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("E")
              })
            GplxComponents().HomeCard("F",
              "600 câu",
              Color(0xFF76DA8D),
              Color(0xFF40DA6B),
              R.drawable.f,
              onClick = {
                navController.navigate("optionscreen") {}
                globalVar.changeLicense("F")
              })
          }
        }
        paddingValues

      }

    }
    MyDialog().FNDialog(globalVar = globalVar)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  @Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
  @Composable
  fun GreetingPreview() {
    GPLXTheme {

    }
  }

}