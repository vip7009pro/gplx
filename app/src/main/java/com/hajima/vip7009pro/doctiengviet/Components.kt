package com.hajima.vip7009pro.doctiengviet

import android.provider.CalendarContract
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hajima.vip7009pro.doctiengviet.ui.theme.DocTiengVietTheme

class Components {
    @Composable
    fun LoginScreen() {
        var username by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginHeader()
                LoginField(username, password, onUserNameChange = {
                    username = it
                    

                }, onPasswordChange = {
                    password = it

                })
                LoginFooter()
            }

        }
    }

    @Composable
    fun LoginHeader() {
        Text(
            text = "Welcome Back",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
        Text(text = "Login to use ERP", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
    }

    @Composable
    fun ColumnScope.LoginField(
        username: String,
        password: String,
        onUserNameChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit
    ) {
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

    @Composable
    fun LoginFooter() {
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Sign in")
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
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )

    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        DocTiengVietTheme {
            LoginScreen()
        }
    }

}