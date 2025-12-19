package com.stbn.quickrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stbn.quickrecipes.core.navigation.Routes
import com.stbn.quickrecipes.features.auth.presentation.login.LoginScreenRoot
import com.stbn.quickrecipes.ui.theme.QuickRecipesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickRecipesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Routes.Login,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Routes.Login> {
                            LoginScreenRoot(
                                onRegisterClick = { navController.navigate(Routes.Register) }
                            )
                        }

                        composable<Routes.Register> {
                            Text(
                                "register"
                            )
                        }

                        composable<Routes.Home> {

                        }
                    }
                }
            }
        }
    }
}
