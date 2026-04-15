package com.stbn.quickrecipes.features.profile.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stbn.quickrecipes.core.presentation.ObserveAsEvents
import com.stbn.quickrecipes.core.presentation.components.TopBar
import com.stbn.quickrecipes.features.profile.presentation.components.ProfileCard

@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ProfileEvent.OnLogout -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    "Logout exitoso",
                    Toast.LENGTH_LONG
                ).show()
                onLogoutSuccess()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Mi Perfil",
            onBackClick = onBackClick
        )
        ProfileScreen(
            modifier = modifier,
            state = state,
            onAction = { action ->
                when (action) {
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileCard(
            user = state.user
        )
        Button(
            onClick = { onAction(ProfileAction.OnLogoutClick) },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Row(
                Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = null
                )
                Text(text = "Cerrar Sesión")
            }
        }
    }
}