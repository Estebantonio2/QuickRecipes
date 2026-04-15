package com.stbn.quickrecipes.features.recipes.domain.model

data class Ingredient(
    val id: Int,
    val image: String,
    val name: String,
    val amount: Double,
    val unit: String,
    val description: String
)
