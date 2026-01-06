package com.stbn.quickrecipes.features.recipes.presentation.detail

import com.stbn.quickrecipes.features.recipes.domain.model.RecipeDetail

data class DetailState(
    val isFetchingRecipe: Boolean = false,
    val recipe: RecipeDetail ?= null,
)
