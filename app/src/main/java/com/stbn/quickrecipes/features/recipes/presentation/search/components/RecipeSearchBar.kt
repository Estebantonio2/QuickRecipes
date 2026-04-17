@file:OptIn(ExperimentalMaterial3Api::class)

package com.stbn.quickrecipes.features.recipes.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.stbn.quickrecipes.features.recipes.domain.model.Recipe

@Composable
fun RecipeSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    recipesResult: List<Recipe>,
    onSearch: (String) -> Unit,
    isLoading: Boolean,
    onTrailIconClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            windowInsets = WindowInsets(0.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = value,
                    onQueryChange = { onValueChange(it) },
                    onSearch = {
                        onSearch(it)
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = {
                        Text("Search")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "buscar"
                        )
                    },
                    trailingIcon = {
                        when {
                            isLoading && expanded-> {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                            else -> {
                                IconButton(
                                    onClick = onTrailIconClick
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = "close"
                                    )
                                }
                            }
                        }
                    }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            val filteredItems = remember(value, recipesResult) {
                if (value.isEmpty() || value.isBlank()) {
                    emptyList()
                } else {
                    val cleanValue = value.trim().replace("\\s+".toRegex(), " ")
                    if (cleanValue.isEmpty()) {
                        emptyList()
                    } else {
                        recipesResult.filter { it.name.contains(cleanValue, ignoreCase = true) }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                expanded = false
                                focusManager.clearFocus()
                            }
                        )
                    }
            ) {
                LazyColumn {
                    items(filteredItems) { recipe ->
                        ListItem(
                            headlineContent = { Text(recipe.name) },
                            modifier = Modifier
                                .clickable {
                                    onValueChange(recipe.name)
                                    expanded = false
                                    onItemClick(recipe.id)
                                }
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}