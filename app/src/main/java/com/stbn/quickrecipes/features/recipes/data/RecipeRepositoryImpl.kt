package com.stbn.quickrecipes.features.recipes.data

import com.stbn.quickrecipes.core.data.networking.get
import com.stbn.quickrecipes.core.util.DataError
import com.stbn.quickrecipes.core.util.Result
import com.stbn.quickrecipes.core.util.map
import com.stbn.quickrecipes.features.recipes.data.mappers.toDomain
import com.stbn.quickrecipes.features.recipes.data.remote.dto.RecipeResponse
import com.stbn.quickrecipes.features.recipes.domain.RecipeRepository
import com.stbn.quickrecipes.features.recipes.domain.model.Recipe
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor (
    private val httpClient: HttpClient
): RecipeRepository {
    override suspend fun fetchRecipes(number: Int): Result<List<Recipe>, DataError.Network> {
        return withContext(Dispatchers.IO) {
            httpClient.get<RecipeResponse>(
                route = "/random",
                queryParameters = mapOf("number" to number)
            ).map { response ->
                response.recipes.map { it.toDomain() }
            }
        }
    }
}