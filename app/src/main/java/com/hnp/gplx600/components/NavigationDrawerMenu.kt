package com.hnp.gplx600.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hnp.gplx600.api.ErpInterface
import kotlinx.coroutines.CoroutineScope

class NavigationDrawerMenu {
  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun NavMenu(
    items: List<ErpInterface.MenuData>, scaffoldState: DrawerState, coroutineScope: CoroutineScope
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())
    ) {
      items.forEach { menu ->
        menu.subMenu?.let {
          if(menu.subMenu!!.size >0)
            MainMenuItem(
            isSelected = false,
            icon = menu.icon,
            title = menu.title,
            subMenuList = it,
            scaffoldState = scaffoldState,
            coroutineScope = coroutineScope
          )
        }
      }
    }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun MainMenuItem(
    isSelected: Boolean,
    icon: @Composable () -> Unit,
    title: String,
    subMenuList: List<ErpInterface.SubMenuData>,
    scaffoldState: DrawerState,
    coroutineScope: CoroutineScope
  ) {
    val colors = MaterialTheme.colorScheme
    val background = MaterialTheme.colorScheme.background
    var isSubMenuVisible by remember { mutableStateOf(false) }
    Column(
      modifier = Modifier
        .background(Color.Transparent)
        .padding(vertical = 5.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = {
            isSubMenuVisible = !isSubMenuVisible
          })
          .padding(10.dp)
          .background(background), verticalAlignment = Alignment.CenterVertically
      ) {
        icon.invoke()
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          text = title, style = MaterialTheme.typography.bodyMedium.copy(
            color = if (isSelected) colors.onPrimary else colors.onSurface
          )
          , fontWeight = FontWeight.Bold
        )
      }
      if (subMenuList?.size!! > 0) {
        SubMenuItem(
          items = subMenuList!!,
          isVisible = isSubMenuVisible,
          scaffoldState = scaffoldState,
          coroutineScope = coroutineScope
        )
        if(isSubMenuVisible){
        Divider()
        }
      }
    }

  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun SubMenuItem(
    items: List<ErpInterface.SubMenuData>,
    isVisible: Boolean,
    scaffoldState: DrawerState,
    coroutineScope: CoroutineScope
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .padding(horizontal = 10.dp)
    ) {

      if (isVisible && items.isNotEmpty()) {
        items.forEach { subItem ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable(onClick = { subItem.onClick.invoke() })
              .padding(10.dp)
              .background(color = Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
          ) {
            subItem.icon.invoke()
            Spacer(modifier = Modifier.width(16.dp))
            Text(
              text = subItem.title, style = MaterialTheme.typography.bodyMedium.copy()
            )
          }
        }
      }
    }
  }

}