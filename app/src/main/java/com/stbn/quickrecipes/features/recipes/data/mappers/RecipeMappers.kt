package com.stbn.quickrecipes.features.recipes.data.mappers

import com.stbn.quickrecipes.features.recipes.data.remote.dto.IngredientDto
import com.stbn.quickrecipes.features.recipes.data.remote.dto.RecipeDetailDto
import com.stbn.quickrecipes.features.recipes.data.remote.dto.RecipeDto
import com.stbn.quickrecipes.features.recipes.data.remote.dto.StepDto
import com.stbn.quickrecipes.features.recipes.domain.model.Ingredient
import com.stbn.quickrecipes.features.recipes.domain.model.Recipe
import com.stbn.quickrecipes.features.recipes.domain.model.RecipeDetail
import com.stbn.quickrecipes.features.recipes.domain.model.Step

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
    return RecipeDetail(
        id = id,
        imageUrl = image,
        name = title,
        durationMin = readyInMinutes,
        source = sourceName,
        description = summary,
        ingredients = extendedIngredients.map { ingredientDto ->
            ingredientDto.toDomain()
        },
        steps = analyzedInstructions.flatMap {
            it.steps.map { stepDto ->
                stepDto.toDomain()
            }
        },
    )
}

fun IngredientDto.toDomain(): Ingredient {
    return Ingredient(
        id = id,
        image = image,
        name = name,
        amount = amount,
        unit = unit,
        description = original
    )
}

fun StepDto.toDomain(): Step {
    return Step(
        number = number,
        description = step
    )
}