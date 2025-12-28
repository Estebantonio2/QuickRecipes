package com.stbn.quickrecipes.features.auth.presentation.register

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stbn.quickrecipes.core.presentation.ObserveAsEvents
import com.stbn.quickrecipes.features.auth.presentation.components.AuthTextField

@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onLoginClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    "Registro exitoso",
                    Toast.LENGTH_LONG
                ).show()
                onLoginClick()
            }
        }
    }

    RegisterScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is RegisterAction.OnLoginClick -> onLoginClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        modifier = modifier
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxHeight(0.9f).fillMaxWidth(0.9f),
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
                Text("Crear Cuenta")
                AuthTextField(
                    title = "Nombre",
                    value = state.name,
                    placeholder = "Nombre",
                    onValueChange = { onAction(RegisterAction.OnNameChange(it)) }
                )
                AuthTextField(
                    title = "Correo Electrónico",
                    value = state.email,
                    placeholder = "example@email.com",
                    onValueChange = { onAction(RegisterAction.OnEmailChange(it)) }
                )
                AuthTextField(
                    title = "Contraseña",
                    value = state.password,
                    placeholder = "********",
                    isSecret = true,
                    onValueChange = { onAction(RegisterAction.OnPasswordChange(it)) },
                    isPasswordVisible = state.isPasswordVisible,
                    onPasswordVisibilityChange = { onAction(RegisterAction.OnPasswordVisibilityChange) }
                )
                AuthTextField(
                    title = "Confirmar Contraseña",
                    value = state.confirmPassword,
                    placeholder = "********",
                    isSecret = true,
                    onValueChange = { onAction(RegisterAction.OnConfirmPasswordChange(it)) },
                    isPasswordVisible = state.isConfirmPasswordVisible,
                    onPasswordVisibilityChange = { onAction(RegisterAction.OnConfirmPasswordVisibilityChange) }
                )
                Button(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onClick = { onAction(RegisterAction.OnRegisterClick) },
                    enabled = state.canRegister,
                    shape = RoundedCornerShape(25)
                ) {
                    Text("Crear cuenta")
                }
                Row {
                    Text(
                        text = "¿Ya tienes cuenta? "
                    )
                    Text(
                        modifier = Modifier.clickable { onAction(RegisterAction.OnLoginClick) },
                        text = "Inicia Sesión",
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}