package com.stbn.quickrecipes.features.recipes.presentation.search

import com.stbn.quickrecipes.core.presentation.util.UiText

sealed interface SearchEvent {
    data class Error(val error: UiText): SearchEvent
}