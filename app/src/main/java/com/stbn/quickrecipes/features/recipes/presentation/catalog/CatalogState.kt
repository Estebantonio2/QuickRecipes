package com.stbn.quickrecipes.features.recipes.presentation.catalog

import com.stbn.quickrecipes.features.recipes.domain.model.Recipe

data class CatalogState(
    val isFetchingRecipes: Boolean = false,
    val isRefreshing: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val userName: String = ""
)
