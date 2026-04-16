package com.stbn.quickrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stbn.quickrecipes.core.navigation.NavigationRoot
import com.stbn.quickrecipes.core.presentation.components.BottomNavigationBar
import com.stbn.quickrecipes.ui.theme.QuickRecipesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickRecipesTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
//                    containerColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (mainViewModel.isLoggedIn) {
                            BottomNavigationBar(
                                navController = navController,
                                currentDestination = currentDestination,
                            )
                        }
                    }
                ) { innerPadding ->
                    NavigationRoot(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        isLoggedIn = mainViewModel.isLoggedIn
                    )
                }
            }
        }
    }
}
