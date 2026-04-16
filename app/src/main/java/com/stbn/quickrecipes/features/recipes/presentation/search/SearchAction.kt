package com.stbn.quickrecipes.features.recipes.presentation.search

sealed interface SearchAction {
    data object OnSearchClick: SearchAction
    data class OnSearchChange(val search: String): SearchAction
    data class OnRecipeClick(val id: Int): SearchAction
    data object OnClearClick: SearchAction
    data class OnCuisineClick(val cuisine: String): SearchAction
    data object OnClearCuisines: SearchAction
}