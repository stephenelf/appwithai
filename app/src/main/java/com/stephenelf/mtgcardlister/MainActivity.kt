package com.stephenelf.mtgcardlister


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.stephenelf.mtgcardlister.presentation.card_list.CardListScreen
import com.stephenelf.mtgcardlister.ui.theme.MTGCardsTheme
import dagger.hilt.android.AndroidEntryPoint

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
                    // Display the main card list screen
                    CardListScreen()
                }
            }
        }
    }
}
