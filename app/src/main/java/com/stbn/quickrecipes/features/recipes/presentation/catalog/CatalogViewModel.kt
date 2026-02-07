package com.stbn.quickrecipes.features.recipes.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stbn.quickrecipes.core.presentation.util.asUiText
import com.stbn.quickrecipes.core.util.Result
import com.stbn.quickrecipes.features.auth.domain.AuthRepository
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
class CatalogViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(CatalogState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<CatalogEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        fetchUserName()
        fetchRecipes(isRefresh = false)
    }

    fun onAction(action: CatalogAction) {
        when (action) {
            CatalogAction.OnRefresh -> fetchRecipes(isRefresh = true)
            else -> Unit
        }
    }

    private fun fetchUserName() {
        val user = authRepository.getCurrentUser()
        if (user != null) {
            _state.update { it.copy(userName = user.name) }
        }
    }

    private fun fetchRecipes(isRefresh: Boolean) {
        viewModelScope.launch {
            _state.update {
                if (isRefresh) it.copy(isRefreshing = true)
                else it.copy(isFetchingRecipes = true)
            }

            when (val result = recipeRepository.fetchRecipes()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            recipes = result.data,
                            isRefreshing = false,
                            isFetchingRecipes = false
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            isFetchingRecipes = false
                        )
                    }
                    eventChannel.send(CatalogEvent.Error(result.error.asUiText()))
                }
            }
        }
    }
}