package com.stbn.quickrecipes.features.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        modifier = modifier,
        onAction = { action ->
            when (action) {
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
//    state = ,
    onAction: (ProfileAction) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),

    ) { }
}