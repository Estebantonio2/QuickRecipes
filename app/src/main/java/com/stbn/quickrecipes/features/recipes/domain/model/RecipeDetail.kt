package com.stbn.quickrecipes.features.recipes.domain.model

data class RecipeDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val source: String,
    val durationMin: Int,
    val description: String,
    val ingredients: List<Ingredient>,
    val steps: List<Step>
)
