@file:OptIn(FlowPreview::class)

package com.stbn.quickrecipes.features.recipes.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stbn.quickrecipes.core.util.Result
import com.stbn.quickrecipes.features.recipes.domain.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private val _forceSearch = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        observeSearchQuery()
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnRecipeClick -> {}
            is SearchAction.OnSearchChange -> {
                _state.update { it.copy(search = action.search) }
            }
            SearchAction.OnSearchClick -> {
                _forceSearch.tryEmit(state.value.search)
            }
            SearchAction.OnClearClick -> clearSearch()
            is SearchAction.OnCuisineClick -> {
                selectCuisine(action.cuisine)
            }

            SearchAction.OnClearCuisines -> {
                _state.update { it.copy(cuisines = emptySet()) }
            }
        }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            val debouncedSearch = _state
                .map { it.search }
                .debounce(1000L)

            merge(debouncedSearch, _forceSearch)
                .map { rawQuery ->
                    rawQuery.trim().replace("\\s+".toRegex(), " ")
                }
                .distinctUntilChanged()
                .collectLatest { cleanQuery ->
                    if (cleanQuery.isEmpty()) {
                        _state.update { it.copy(recipesResult = emptyList(), isLoading = false) }
                    } else {
                        searchRecipes(cleanQuery)
                    }
                }
        }
    }

    private suspend fun searchRecipes(search: String) {
        _state.update { it.copy(isLoading = true) }
        try {
            when (val response = recipeRepository.fetchRecipesSearch(search)) {
                is Result.Success -> {
                    _state.update { it.copy(recipesResult = response.data) }
                }
                is Result.Error -> {

                }
            }
        } finally {
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun clearSearch() {
        _state.update { it.copy(search = "", isLoading = false) }
    }

    private fun selectCuisine(cuisine: String) {
        if (cuisine in state.value.cuisines) {
            _state.update { it.copy(cuisines = state.value.cuisines.minusElement(cuisine)) }
        } else {
            _state.update { it.copy(cuisines = state.value.cuisines.plusElement(cuisine)) }
        }
    }
}