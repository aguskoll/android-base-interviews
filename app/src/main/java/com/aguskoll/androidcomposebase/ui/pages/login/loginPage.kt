package com.aguskoll.androidcomposebase.ui.pages.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginPage(onLoginSuccess: () -> Unit) {
    var userNameInput by remember { mutableStateOf("") }
    val viewModel: LogInViewModel = koinViewModel()
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val event: LoginEvent? by viewModel.eventFlow.collectAsStateWithLifecycle(null)

    LaunchedEffect(event) {
        when (event) {
            is LoginEvent.LoginSuccess -> onLoginSuccess()
            is LoginEvent.LoginError -> {
                // Handle error
            }

            else -> {

            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Welcome")
            TextField(
                value = uiState.email,
                onValueChange = { value ->
                    viewModel.onEmailChanged(value)
                },
                label = { Text("Email") },
                isError = !uiState.isEmailValid,
                supportingText = {
                    if (!uiState.isEmailValid) {
                        "Email must be valid"
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = uiState.password,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    viewModel.onPasswordChanged(it)
                },
                isError = !uiState.isPasswordValid,
                supportingText = {
                    if (!uiState.isPasswordValid) {
                      Text(
                          "Password must be at least 8 characters long",
                          color = Color.Red,
                      )

                    }
                },
            )
            Button(onClick = { viewModel.onLoginClick() }) {
                Text("Login")
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun LoginPagePreview() {
    LoginPage({})
}

