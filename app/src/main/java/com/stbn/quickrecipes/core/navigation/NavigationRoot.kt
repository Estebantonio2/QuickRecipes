package com.stbn.quickrecipes.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.stbn.quickrecipes.features.auth.presentation.login.LoginScreenRoot
import com.stbn.quickrecipes.features.auth.presentation.register.RegisterScreenRoot
import com.stbn.quickrecipes.features.profile.presentation.ProfileScreenRoot
import com.stbn.quickrecipes.features.recipes.presentation.catalog.CatalogScreenRoot
import com.stbn.quickrecipes.features.recipes.presentation.detail.DetailScreenRoot
import com.stbn.quickrecipes.features.recipes.presentation.search.SearchScreenRoot

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isLoggedIn) Routes.RecipesSearch else Routes.AuthGraph
    ) {
        authGraph(navController)
        recipesGraph(navController)
        profileGraph(navController)

        composable<Routes.RecipesSearch> {
            SearchScreenRoot()
        }
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<Routes.AuthGraph>(
        startDestination = Routes.Login,
    ) {
        composable<Routes.Login> {
            LoginScreenRoot(
                onRegisterClick = { navController.navigate(Routes.Register) },
                onLoginSuccess = {
                    navController.navigate(Routes.RecipesGraph) {
                        popUpTo(Routes.AuthGraph) { inclusive = true }
                    }
                }
            )
        }

        composable<Routes.Register> {
            RegisterScreenRoot(
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

private fun NavGraphBuilder.recipesGraph(navController: NavHostController) {
    navigation<Routes.RecipesGraph>(
        startDestination = Routes.RecipesCatalog,
    ) {
        composable<Routes.RecipesCatalog> {
            CatalogScreenRoot(
                onRecipeDetailClick = { recipeId ->
                    navController.navigate(Routes.RecipesDetail(id = recipeId))
                }
            )
        }

        composable<Routes.RecipesDetail> {
            DetailScreenRoot(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

private fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    navigation<Routes.ProfileGraph>(
        startDestination = Routes.ProfileMenu
    ) {
        composable<Routes.ProfileMenu> {
            ProfileScreenRoot(
                onLogoutSuccess = {
                    navController.navigate(Routes.AuthGraph){
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}