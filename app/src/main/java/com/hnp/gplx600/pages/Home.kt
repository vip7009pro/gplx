package com.hnp.gplx600.pages

import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hnp.gplx600.R
import com.hnp.gplx600.api.ErpInterface
import com.hnp.gplx600.api.GlobalVariable
import com.hnp.gplx600.api.LocalData
import com.hnp.gplx600.components.MyDialog
import com.hnp.gplx600.components.NavigationDrawerMenu
import com.hnp.gplx600.theme.CMSVTheme
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.launch

class Home {
  @RequiresApi(Build.VERSION_CODES.O)
  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun MyHome(navController: NavController, globalVar: GlobalVariable) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    //val globalVar = viewModel<GlobalVariable>()
    val currentContext = LocalContext.current
    val menuData = listOf(
      ErpInterface.MenuData(
        route = "nhansubophan",
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
            navController.navigate("diemdanhnhom") {
              popUpTo("diemdanhnhom") {
                inclusive = true
              }
            }
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
            Row(
              verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                  model = "http://14.160.33.94/Picture_NS/NS_${globalVar.userData.EMPL_NO}.jpg",
                  contentDescription = "employee image",
                  modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(
                      RoundedCornerShape(50.dp)
                    ),
                  contentScale = ContentScale.FillBounds
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                  Text(
                    text = "Họ tên: ${globalVar.userData.MIDLAST_NAME + " " + globalVar.userData.FIRST_NAME} ",
                    color = if (isDarkMode) Color.Yellow else Color("#0937CD".toColorInt()),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = "Bộ phận: ${globalVar.userData.MAINDEPTNAME} ",
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = "Chức vụ: ${globalVar.userData.JOB_NAME} ",
                    color = Color("#059A15".toColorInt()),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = "Mã NV: ${globalVar.userData.EMPL_NO} ",
                    color = if (isDarkMode) Color.White else Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Button(
                    onClick = {
                      globalVar.showDialog("warning", "Thông báo", "Chắc chắn muốn logout?", {
                        navController.navigate("login") {
                          popUpTo("login") {
                            inclusive = true
                          }
                        }
                        LocalData().saveData(currentContext, "token", "reset")
                      }, {})
                    }, modifier = Modifier
                      .width(90.dp)
                      .height(40.dp)
                  ) {
                    Text(text = "Logout", fontSize = 12.sp)
                  }
                }
              }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
            NavigationDrawerMenu().NavMenu(
              items = menuData, scaffoldState = drawerState, coroutineScope = scope
            )
          }
        }
      },
    ) {
      Scaffold(
        topBar = {
          TopAppBar(modifier = Modifier.height(40.dp),
            colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
              containerColor = MaterialTheme.colorScheme.primaryContainer,
              titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {})

        },
        /*bottomBar = {
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
        },*/
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
        floatingActionButtonPosition = FabPosition.End,
      ) { paddingValues ->

        Column(modifier = Modifier.padding(top = 40.dp)) {

          paddingValues
        }

      }

    }
    MyDialog().FNDialog(globalVar = globalVar)
  }


  @OptIn(ExperimentalFoundationApi::class)
  @Composable
  fun HorizontalViewPager() {
    val page = 10
    val pagerState = rememberPagerState(
      initialPage = 3,
      initialPageOffsetFraction = .25f
    ) {
      page
            // provide pageCount
    }


  }
  @RequiresApi(Build.VERSION_CODES.O)
  @Preview(showBackground = true, showSystemUi = true)
  @Composable
  fun GreetingPreview() {
    CMSVTheme {

    }
  }

}