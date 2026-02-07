package com.stbn.quickrecipes.features.recipes.domain

import com.stbn.quickrecipes.core.util.DataError
import com.stbn.quickrecipes.core.util.Result
import com.stbn.quickrecipes.features.recipes.domain.model.Recipe
import com.stbn.quickrecipes.features.recipes.domain.model.RecipeDetail

interface RecipeRepository {
    suspend fun fetchRecipes(number: Int = 5): Result<List<Recipe>, DataError.Network>
    suspend fun fetchRecipeDetail(id: Int): Result<RecipeDetail, DataError.Network>
}