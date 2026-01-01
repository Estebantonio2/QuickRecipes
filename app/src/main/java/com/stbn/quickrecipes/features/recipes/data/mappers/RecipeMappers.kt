package com.stbn.quickrecipes.features.recipes.data.mappers

import com.stbn.quickrecipes.features.recipes.data.remote.dto.RecipeDto
import com.stbn.quickrecipes.features.recipes.domain.model.Recipe

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = id,
        imageUrl = image,
        name = title,
        durationMin = readyInMinutes,
        source = sourceName,
    )
}