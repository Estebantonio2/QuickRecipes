package com.stbn.quickrecipes.features.recipes.data.mappers

import com.stbn.quickrecipes.features.recipes.data.remote.dto.RecipeDetailDto
import com.stbn.quickrecipes.features.recipes.data.remote.dto.RecipeDto
import com.stbn.quickrecipes.features.recipes.domain.model.Recipe
import com.stbn.quickrecipes.features.recipes.domain.model.RecipeDetail

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = id,
        imageUrl = image,
        name = title,
        durationMin = readyInMinutes,
        source = sourceName,
    )
}

fun RecipeDetailDto.toDomain(): RecipeDetail {
    val ingredients = extendedIngredients.map { ingredient ->
        ingredient.original
    }
    val steps = analyzedInstructions.map { stepsContainer ->
        stepsContainer.steps.map { step ->
            step.step
        }
    }.flatten()
    return RecipeDetail(
        id = id,
        imageUrl = image,
        name = title,
        durationMin = readyInMinutes,
        source = sourceName,
        description = summary,
        ingredients = ingredients,
        steps = steps,
    )
}