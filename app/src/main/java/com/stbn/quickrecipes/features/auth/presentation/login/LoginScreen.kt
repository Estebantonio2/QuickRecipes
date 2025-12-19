package com.stbn.quickrecipes.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stbn.quickrecipes.features.auth.presentation.login.components.LoginTextField

@Composable
fun LoginScreenRoot (
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onRegisterClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                is LoginAction.OnRegisterClick -> onRegisterClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun LoginScreen (
    modifier: Modifier = Modifier,
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxHeight(0.6f).fillMaxWidth(0.8f),
            shape = RoundedCornerShape(5),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(defaultElevation = 100.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("Bienvenido")
                LoginTextField(
                    title = "Correo Electrónico",
                    value = state.email,
                    placeholder = "example@email.com",
                    onValueChange = { onAction(LoginAction.OnEmailChange(it)) }
                )
                LoginTextField(
                    title = "Contraseña",
                    value = state.password,
                    placeholder = "********",
                    isSecret = true,
                    onValueChange = { onAction(LoginAction.OnPasswordChange(it)) }
                )
                Button(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onClick = { onAction(LoginAction.OnLoginClick) },
                    enabled = state.canLogin,
                    shape = RoundedCornerShape(25)
                ) {
                    Text("Iniciar Sesión")
                }
                Row {
                    Text(
                        text = "¿No tienes cuenta? "
                    )
                    Text(
                        modifier = Modifier.clickable { onAction(LoginAction.OnRegisterClick) },
                        text = "Regístrate aquí",
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}