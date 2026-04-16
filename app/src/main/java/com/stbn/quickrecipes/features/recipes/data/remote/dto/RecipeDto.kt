package com.stbn.quickrecipes.features.recipes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val image: String,
    val title: String,
    val readyInMinutes: Int? = null,
    val sourceName: String? = null
)