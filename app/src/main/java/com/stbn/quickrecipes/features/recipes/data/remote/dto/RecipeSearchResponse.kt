package com.stbn.quickrecipes.features.recipes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeSearchResponse(
    val results: List<RecipeDto>
)
