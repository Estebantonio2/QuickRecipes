package com.stbn.quickrecipes.features.recipes.presentation.catalog

sealed interface CatalogAction {
    data class OnRecipeDetailClick(val id: Int): CatalogAction
    data object OnRefresh: CatalogAction
}