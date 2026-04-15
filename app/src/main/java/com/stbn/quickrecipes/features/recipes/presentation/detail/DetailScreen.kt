package com.stbn.quickrecipes.features.recipes.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Dining
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stbn.quickrecipes.core.presentation.ObserveAsEvents
import com.stbn.quickrecipes.core.presentation.components.TopBar
import com.stbn.quickrecipes.features.recipes.presentation.components.DetailItem

@Composable
fun DetailScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is DetailEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopBar(
            title = state.recipe?.name ?: "",
            onBackClick = onBackClick
        )
        DetailScreen(
            modifier = Modifier.weight(1f),
            state = state
        )
    }
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState
) {
    if (state.isFetchingRecipe) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (state.recipe != null) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 15.dp)
            ) {
                item {
                    DetailItem(
                        image = state.recipe.imageUrl
                    )
                }
                item {
                    DetailItem(
                        title = state.recipe.name,
                        description = state.recipe.description,
                        subtitles = listOf(
                            Icons.Outlined.PersonOutline to state.recipe.source,
                            Icons.Outlined.AccessTime to "${state.recipe.durationMin} min"
                        )
                    )
                }
                item {
                    DetailItem(
                        titleIcon = Icons.Outlined.Dining,
                        title = "Ingredientes",
                        recipe = state.recipe
                    )
                }
                item {
                    DetailItem(
                        title = "Pasos de Preparación",
                        isListed = true,
                        recipe = state.recipe
                    )
                }
            }
        }
    }
}