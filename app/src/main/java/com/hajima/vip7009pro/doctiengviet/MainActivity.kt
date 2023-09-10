package com.hajima.vip7009pro.doctiengviet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hajima.vip7009pro.doctiengviet.ui.theme.DocTiengVietTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocTiengVietTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Red) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun CommonSpace() {
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun HomeScreen() {
    Column {
        CustomRadioButton(title = "Nguyen Van Hung 3")
        CommonSpace()
        CustomRadioButton(title = "Nguyen Thi Ngoc Giang")
    }
}

@Composable
fun CustomRadioButton(title: String) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.selectable(
            selected = isSelected, onClick = { isSelected = !isSelected }, role = Role.RadioButton
        )
    ) {
        RadioButton(
            selected = isSelected, onClick = null, colors = RadioButtonDefaults.colors(
                selectedColor = Color.Red,
                unselectedColor = Color.Gray,
                disabledSelectedColor = Color.Magenta,
                disabledUnselectedColor = Color.Green
            )
        )
        Text(text = title, modifier = Modifier.padding(start = 16.dp))

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    DocTiengVietTheme {
        HomeScreen()
    }
}
