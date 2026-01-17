package com.stbn.quickrecipes.features.recipes.presentation.detail

import androidx.core.text.HtmlCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.stbn.quickrecipes.core.navigation.Routes
import com.stbn.quickrecipes.core.presentation.util.asUiText
import com.stbn.quickrecipes.core.util.Result
import com.stbn.quickrecipes.features.recipes.domain.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
): ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<DetailEvent>()
    val events = eventChannel.receiveAsFlow()

    private val recipeId = savedStateHandle.toRoute<Routes.RecipesDetail>().id

    init {
        fetchRecipeDetail()
    }

    private fun fetchRecipeDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isFetchingRecipe = true) }
            when (val result = recipeRepository.fetchRecipeDetail(recipeId)) {
                is Result.Success -> {
                    val cleanResult = result.data.copy(description = cleanDescription(result.data.description))
                    _state.update { it.copy(recipe = cleanResult) }
                }
                is Result.Error -> {
                    eventChannel.send(DetailEvent.Error(result.error.asUiText()))
                }
            }
            _state.update { it.copy(isFetchingRecipe = false) }
        }
    }

    private fun cleanDescription(description: String): String {
        return HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
    }
}