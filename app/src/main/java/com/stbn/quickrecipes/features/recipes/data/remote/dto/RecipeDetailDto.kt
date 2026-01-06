package com.stbn.quickrecipes.features.recipes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailDto(
    val id: Int,
    val image: String,
    val title: String,
    val readyInMinutes: Int,
    val sourceName: String,
    val extendedIngredients: List<Ingredient>,
    val summary: String,
    val analyzedInstructions: List<InstructionsContainer>
)

@Serializable
data class Ingredient(
    val original: String
)

@Serializable
data class InstructionsContainer(
    val steps: List<InstructionStep>
)

@Serializable
data class InstructionStep(
    val step: String
)