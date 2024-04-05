package com.example.students.todo.ui.auth
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.students.core.ui.UserPreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLogin: () -> Unit) {
    val loginViewModel = viewModel<LoginViewModel>(factory = LoginViewModel.Factory())
    val userPreferencesViewModel = viewModel<UserPreferencesViewModel>(factory = UserPreferencesViewModel.Factory)
    val token = loginViewModel.token
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loginMessage by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(token) {
        Log.d("LoginScreen", "token = $token")
        if (token != null) {
            if (token != "") {
                Log.d("LoginScreen", "Login successful")
                userPreferencesViewModel.saveToken(token)
                onLogin()
            } else {
                Log.d("LoginScreen", "Login failed")
                loginMessage = "Login failed"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Login") },
                actions = {
                    Button(onClick = {
                        Log.d("LoginScreen", "login username = $username, password = $password");
                        loginViewModel.login(username, password)
                    }) { Text("Login") }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Row {
                TextField(
                    value = username,
                    onValueChange = { username = it }, label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Row {
                TextField(
                    value = password,
                    onValueChange = { password = it }, label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            if (loginMessage != "") {
                Text(loginMessage)
            }
        }
    }
}