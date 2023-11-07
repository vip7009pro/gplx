package com.cmsbando.erp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerDefaults.scrimColor
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmsbando.erp.theme.CMSVTheme
import kotlinx.coroutines.launch

class Home {

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun MyHome() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
      drawerState = drawerState,
      drawerContent = {
        ModalDrawerSheet {
          Text("Drawer title", modifier = Modifier.padding(16.dp))
          Divider()
          Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )
            NavigationDrawerItem(
              label = { Text(text = "Drawer Item") },
              selected = false,
              onClick = { /*TODO*/ }
            )

          }


        }
      },
    ) {
      Scaffold(topBar = {
        TopAppBar(colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
          Text("CMS VINA")
        })

      }, bottomBar = {
        BottomAppBar(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          contentColor = MaterialTheme.colorScheme.primary,
        ) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Bottom app bar",
          )
        }
      }, floatingActionButton = {
        FloatingActionButton(onClick = { scope.launch {
          drawerState.apply {
            if (isClosed) open() else close()
          }
        } }, ) {
          Icon(Icons.Default.Menu, contentDescription = "Menu")
        }

      }, floatingActionButtonPosition = FabPosition.End
      ) { innerPadding ->
        Column(
          modifier = Modifier.padding(innerPadding),
          verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          Text(
            modifier = Modifier.padding(8.dp),
            text = """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button  times.
                """.trimIndent(),
          )
        }
      }

    }

  }

  @Preview(showBackground = true, showSystemUi = true)
  @Composable
  fun GreetingPreview() {
    CMSVTheme {
      MyHome()
    }
  }

}