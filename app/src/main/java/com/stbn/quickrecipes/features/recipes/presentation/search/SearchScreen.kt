package com.stbn.quickrecipes.features.recipes.presentation.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stbn.quickrecipes.core.presentation.components.TopBar
import com.stbn.quickrecipes.features.recipes.presentation.search.components.CuisineItem
import com.stbn.quickrecipes.features.recipes.presentation.search.components.RecipeSearchBar

@Composable
fun SearchScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
            targetState = state.cuisines.isEmpty(),
            modifier = Modifier.weight(1f),
            label = "CuisinesTransition"
        ) { isEmpty ->
            if (isEmpty) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.weight(1f))
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
                                onClick = { onAction(SearchAction.OnCuisineClick(it)) }
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LazyRow(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            stickyHeader {
                                IconButton(
                                    onClick = { onAction(SearchAction.OnClearCuisines) },
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
                            items(
                                items = cuisines.sortedBy { it !in state.cuisines },
                                key = { it }
                            ) { cuisine ->
                                CuisineItem(
                                    modifier = Modifier.animateItem(),
                                    cuisine = cuisine,
                                    selected = cuisine in state.cuisines,
                                    onClick = { onAction(SearchAction.OnCuisineClick(cuisine)) }
                                )
                            }
                            item { Spacer(Modifier.width(10.dp)) }
                        }
                    }
                }
            }
        }
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

