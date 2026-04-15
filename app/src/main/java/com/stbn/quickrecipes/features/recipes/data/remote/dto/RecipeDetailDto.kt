package com.stbn.quickrecipes.features.recipes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailDto(
    val id: Int,
    val image: String,
    val title: String,
    val readyInMinutes: Int,
    val sourceName: String,
    val extendedIngredients: List<IngredientDto>,
    val summary: String,
    val analyzedInstructions: List<StepsContainer>
)

@Serializable
data class IngredientDto(
    val id: Int,
    val image: String,
    val name: String,
    val amount: Double,
    val unit: String,
    val original: String
)

@Serializable
data class StepsContainer(
    val steps: List<StepDto>
)

@Serializable
data class StepDto(
    val number: Int,
    val step: String
)