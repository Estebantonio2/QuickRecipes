package com.stbn.quickrecipes.core.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object AuthGraph: Routes

    @Serializable
    data object Login: Routes

    @Serializable
    data object Register: Routes

    @Serializable
    data object RecipesGraph: Routes

    @Serializable
    data object RecipesCatalog: Routes

    @Serializable
    data class RecipesDetail(val id: Int): Routes

    @Serializable
    data object RecipesSearch: Routes

    @Serializable
    data object ProfileGraph: Routes

    @Serializable
    data object ProfileMenu: Routes
}