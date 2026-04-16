package com.stbn.quickrecipes.features.recipes.presentation.search

import com.stbn.quickrecipes.features.recipes.domain.model.Recipe

data class SearchState(
    val search: String = "",
    val recipesResult: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val cuisines: Set<String> = emptySet()
)
