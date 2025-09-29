package com.stephenelf.mtgcardlister


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stephenelf.mtgcardlister.presentation.card_list.CardListScreen
import com.stephenelf.mtgcardlister.ui.theme.MTGCardsTheme
import dagger.hilt.android.AndroidEntryPoint
import com.stephenelf.mtgcardlister.presentation.card_details.CardDetailScreen

/**
 * Main activity of the application.
 * Annotated with [AndroidEntryPoint] to enable Hilt for dependency injection in this activity.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply the custom theme to the entire application
            MTGCardsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Composable
    fun AppNavigation() {
        val navController = rememberNavController() // Create a NavController

        NavHost(navController = navController, startDestination = NavRoutes.CARD_LIST) {
            composable(NavRoutes.CARD_LIST) {
                CardListScreen(
                    onNavigateToDetails = { cardId -> // Pass a lambda for navigation
                        navController.navigate(NavRoutes.cardDetails(cardId))
                    }
                )
            }
            composable(
                route = NavRoutes.CARD_DETAILS,
                arguments = listOf(navArgument("cardId") {
                    type = NavType.StringType
                }) // Define arguments
            ) { backStackEntry ->
                val cardId = backStackEntry.arguments?.getString("cardId")
                CardDetailScreen(cardId = cardId)
            }
        }
    }
}
