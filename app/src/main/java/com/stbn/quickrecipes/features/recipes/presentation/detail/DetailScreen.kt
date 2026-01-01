package com.stbn.quickrecipes.features.recipes.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun DetailScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    DetailScreen(
        modifier = modifier
    )
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier
) {

}