package com.stbn.quickrecipes.features.recipes.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.stbn.quickrecipes.core.navigation.Routes
import com.stbn.quickrecipes.features.recipes.domain.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
): ViewModel() {
    private val recipeId = savedStateHandle.toRoute<Routes.RecipesDetail>().id

    init {
        println("El id seleccionado es $recipeId")
    }
}