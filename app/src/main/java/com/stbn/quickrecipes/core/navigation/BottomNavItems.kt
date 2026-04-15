package com.stbn.quickrecipes.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

object BottomNavItems {
    val navItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = Routes.RecipesGraph
        ),
        BottomNavItem(
            label = "Search",
            icon = Icons.Filled.Search,
            route = Routes.RecipesSearch
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = Routes.ProfileGraph
        )
    )
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: Routes,
)