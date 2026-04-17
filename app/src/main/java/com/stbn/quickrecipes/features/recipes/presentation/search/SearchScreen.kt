package com.stbn.quickrecipes.features.recipes.presentation.search

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.stbn.quickrecipes.features.recipes.presentation.components.CatalogItem
import com.stbn.quickrecipes.features.recipes.presentation.search.components.CuisineItem
import com.stbn.quickrecipes.features.recipes.presentation.search.components.RecipeSearchBar

@Composable
fun SearchScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SearchEvent.Error -> {
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
            title = "Buscar Recetas"
        )
        SearchScreen(
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
fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit
) {
    val isShowingResults = state.cuisines.isNotEmpty() || state.search.isNotBlank() || state.isLoading

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecipeSearchBar(
            value = state.search,
            onValueChange = {
                onAction(SearchAction.OnSearchChange(it))
            },
            isLoading = state.isLoading,
            recipesResult = state.recipesResult,
            onSearch = {
                onAction(SearchAction.OnSearchClick)
            },
            onTrailIconClick = {
                onAction(SearchAction.OnClearClick)
            },
            onItemClick = {}
        )
        AnimatedContent(
            targetState = isShowingResults,
            modifier = Modifier.weight(1f),
            label = "CuisinesTransition"
        ) { isShowingResults ->
            if (!isShowingResults) {
                CuisineSelector(
                    onCuisineClick = {
                        onAction(SearchAction.OnCuisineClick(it))
                    }
                )
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(Modifier.height(12.dp))
                    CuisinesSelection(
                        cuisinesSet = state.cuisines,
                        onIconClick = { onAction(SearchAction.OnClearCuisines) },
                        onCuisineClick = { onAction(SearchAction.OnCuisineClick(it)) }
                    )
                    Spacer(Modifier.height(12.dp))
                    when {
                        state.isLoading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        state.recipesResult.isEmpty() -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No se encontraron recetas"
                                )
                            }
                        }

                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(20.dp),
                                contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 15.dp)
                            ) {
                                items(items = state.recipesResult, key = { it.id }) { recipe ->
                                    CatalogItem(
                                        recipe = recipe,
                                        onClick = { onAction(SearchAction.OnRecipeClick(it)) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CuisineSelector(
    modifier: Modifier = Modifier,
    onCuisineClick: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(0.8f),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cuisines.sortedBy { it }.forEach { cuisine ->
                CuisineItem(
                    cuisine = cuisine,
                    selected = false,
                    onClick = onCuisineClick
                )
            }
        }
    }
}

@Composable
private fun CuisinesSelection(
    modifier: Modifier = Modifier,
    cuisinesSet: Set<String>,
    onIconClick: () -> Unit,
    onCuisineClick: (String) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (cuisinesSet.isNotEmpty()) {
            stickyHeader {
                Spacer(Modifier.width(10.dp))
                IconButton(
                    onClick = onIconClick,
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Limpiar categorías"
                    )
                }
            }
        } else {
            item { Spacer(Modifier.width(10.dp)) }
        }
        items(
            items = cuisines.sortedBy { it !in cuisinesSet },
            key = { it }
        ) { cuisine ->
            CuisineItem(
                modifier = Modifier.animateItem(),
                cuisine = cuisine,
                selected = cuisine in cuisinesSet,
                onClick = onCuisineClick
            )
        }
        item { Spacer(Modifier.width(10.dp)) }
    }
}

private val cuisines = listOf(
    "American",
    "British",
    "Chinese",
    "European",
    "French",
    "Greek",
    "Italian",
    "Japanese",
    "Korean",
    "Latin American",
    "Mediterranean",
    "Mexican",
    "Spanish"
)

