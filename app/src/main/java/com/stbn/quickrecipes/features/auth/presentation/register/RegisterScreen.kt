package com.stbn.quickrecipes.features.auth.presentation.register

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.stbn.quickrecipes.features.auth.presentation.components.AuthTextField

@Composable
fun RegisterScreenRoot (
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    RegisterScreen(
        modifier = modifier
    )
}

@Composable
fun RegisterScreen (
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
                    value = "",
                    onValueChange = {}
                )
                AuthTextField(
                    title = "Correo Electrónico",
                    value = "",
                    onValueChange = {}
                )
                AuthTextField(
                    title = "Contraseña",
                    value = "",
                    onValueChange = {}
                )
                AuthTextField(
                    title = "Confirmar Contraseña",
                    value = "",
                    onValueChange = {}
                )
                Button(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onClick = {  },
//                    enabled = ,
                    shape = RoundedCornerShape(25)
                ) {
                    Text("Crear cuenta")
                }
                Row {
                    Text(
                        text = "¿Ya tienes cuenta? "
                    )
                    Text(
                        modifier = Modifier.clickable {  },
                        text = "Inicia Sesión",
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}