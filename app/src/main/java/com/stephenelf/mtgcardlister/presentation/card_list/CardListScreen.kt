package com.stephenelf.mtgcardlister.presentation.card_list


import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephenelf.mtgcardlister.presentation.card_list.components.FeaturedCardItem
import com.stephenelf.mtgcardlister.presentation.card_list.components.LatestAdditionCardItem
import com.stephenelf.mtgcardlister.presentation.card_list.components.SearchAndFilterSection

import androidx.hilt.navigation.compose.hiltViewModel
import com.stephenelf.mtgcardlister.ui.theme.BottomNavBackground
import com.stephenelf.mtgcardlister.ui.theme.BottomNavItemActive
import com.stephenelf.mtgcardlister.ui.theme.BottomNavItemInactive

/**
 * Main Composable function for the Card List Screen.
 * This screen displays featured cards, latest additions, and provides search/filter functionality.
 * It observes the [CardListViewModel] for UI state updates.
 */
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(
    onNavigateToDetails: (String) -> Unit, // Accept the callback for navigation
    viewModel: CardListViewModel = hiltViewModel() // Injects the ViewModel
) {
    val state = viewModel.state.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cards",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(onClick = { /* Handle settings click */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Use primary color for top bar
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BottomNavBackground, // Custom background color
                contentColor = BottomNavItemInactive // Default content color for inactive items
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Home Button
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = BottomNavItemActive // Active color for Home
                        )
                        Text(
                            text = "Home",
                            style = MaterialTheme.typography.labelSmall,
                            color = BottomNavItemActive
                        )
                    }
                    // Collection Button (example, assuming it's inactive)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Collection",
                            tint = BottomNavItemInactive // Inactive color for Collection
                        )
                        Text(
                            text = "Collection",
                            style = MaterialTheme.typography.labelSmall,
                            color = BottomNavItemInactive
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background // Main screen background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Search and Filter Section
            SearchAndFilterSection(
                searchQuery = searchQuery.value,
                onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
            )

            // Loading Indicator
            if (state.value.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.secondary // Use secondary color for progress bar
                )
            }

            // Error Message
            if (state.value.error != null) {
                Text(
                    text = state.value.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            // Featured Cards Section
            Text(
                text = "Featured Cards",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            if(state.value.cards.isNotEmpty())
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.value.cards.take(5)) { card -> // Display first 5 cards as featured
                    FeaturedCardItem(card = card,
                        onClick = {
                            onNavigateToDetails(card.id) // Call the lambda on click
                        })
                }
            }



            // Latest Additions Section
            Text(
                text = "Latest Additions",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp), // Adaptive grid for responsiveness
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.value.cards.drop(5)) { card -> // Display remaining cards as latest additions
                    LatestAdditionCardItem(card = card)
                }
            }
        }
    }
}
