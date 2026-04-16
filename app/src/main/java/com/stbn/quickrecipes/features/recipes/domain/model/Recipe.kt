package com.stbn.quickrecipes.features.recipes.domain.model

data class Recipe(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val durationMin: Int? = null,
    val source: String? = null
)
