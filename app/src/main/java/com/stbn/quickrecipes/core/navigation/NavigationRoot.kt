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
import com.stbn.quickrecipes.features.recipes.catalog.CatalogScreenRoot

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isLoggedIn) Routes.RecipesGraph else Routes.AuthGraph
    ) {
        authGraph(navController)
        recipesGraph(navController)
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
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
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
            CatalogScreenRoot()
        }
    }
}