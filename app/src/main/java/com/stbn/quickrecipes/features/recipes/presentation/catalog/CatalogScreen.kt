package com.stbn.quickrecipes.features.recipes.presentation.catalog

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stbn.quickrecipes.core.presentation.ObserveAsEvents
import com.stbn.quickrecipes.features.recipes.presentation.components.CatalogItem
import com.stbn.quickrecipes.features.recipes.presentation.components.CatalogTopBar

@Composable
fun CatalogScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: CatalogViewModel = hiltViewModel(),
    onProfileClick: () -> Unit,
    onRecipeDetailClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is CatalogEvent.Error -> {
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
        CatalogTopBar(
            onProfileClick = onProfileClick,
            userName = state.userName,
        )
        CatalogScreen(
            modifier = Modifier.weight(1f),
            state = state,
            onAction = { action ->
                when (action) {
                    is CatalogAction.OnRecipeDetailClick -> onRecipeDetailClick(action.id)
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    state: CatalogState,
    onAction: (CatalogAction) -> Unit
) {
    if (state.isFetchingRecipes) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        PullToRefreshBox(
            modifier = modifier,
            isRefreshing = state.isRefreshing,
            onRefresh = { onAction(CatalogAction.OnRefresh) }
        ) {
            if (state.recipes.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "No se encontraron recetas"
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp)
                ) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = "Explora Nuestras Recetas",
                                fontSize = 20.sp
                            )
                            Text(
                                text = "Descubre platillos deliciosos para cada ocasión"
                            )
                        }
                    }
                    items(items = state.recipes, key = { it.id }) { recipe ->
                        CatalogItem(
                            recipe = recipe,
                            onClick = { onAction(CatalogAction.OnRecipeDetailClick(it))}
                        )
                    }
                }
            }
        }
    }
}