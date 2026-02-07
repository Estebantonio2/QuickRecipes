package com.stbn.quickrecipes.features.recipes.presentation.catalog

import com.stbn.quickrecipes.core.presentation.util.UiText

sealed interface CatalogEvent {
    data class Error(val error: UiText): CatalogEvent
}