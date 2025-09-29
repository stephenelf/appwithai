package com.stephenelf.mtgcardlister.presentation.card_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Data Model ---
/**
 * Data class representing the details of a trading card.
 */
data class CardDetails(
    val name: String,
    val set: String,
    val type: String,
    val rarity: String,
    val description: String
    // In a real app, you would add imageUrl: String here
)

// --- Composable Functions ---

/**
 * The main screen composable for displaying card details.
 */
@Composable
fun CardDetailScreen(details: CardDetails= CardDetails(
    name = "Donthus, The Nurturer",
    set = "Dominaria United",
    type = "Creature",
    rarity = "Rare",
    description = "A powerful creature with unique abilities, capable of turning the tide of battle."
), cardId: String? = "0") {
    // A standard scaffold provides the structure for the screen (TopBar, BottomBar, Content)
    Scaffold(
        topBar = { DetailTopBar() },
        bottomBar = { DetailBottomBar() }
    ) { paddingValues ->
        // LazyColumn ensures the content is scrollable if it exceeds the screen height
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(Modifier.height(24.dp))
                CardImagePlaceholder(cardName = details.name)
                Spacer(Modifier.height(32.dp))

                // Card Information Section Header
                Text(
                    text = "Card Information",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            // Structured Card Properties
            item {
                // Set and Type side-by-side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CardPropertyItem(
                        label = "Set",
                        value = details.set,
                        modifier = Modifier.weight(1f)
                    )
                    CardPropertyItem(
                        label = "Type",
                        value = details.type,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(16.dp))

                // Rarity
                CardPropertyItem(
                    label = "Rarity",
                    value = details.rarity,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))

                // Description
                CardPropertyItem(
                    label = "Description",
                    value = details.description,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

/**
 * Composable for the Top App Bar (Header).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Card Details",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle back button click */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        // Apply elevation/shadow to TopAppBar
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

/**
 * Composable for the large card image placeholder.
 */
@Composable
fun CardImagePlaceholder(cardName: String) {
    // This Box simulates a card with a fixed aspect ratio similar to the image
    Box(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .aspectRatio(0.72f) // Standard MTG card aspect ratio is about 0.72
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0077B6)), // A placeholder color (Blue for Water/Island)
        contentAlignment = Alignment.Center
    ) {
        // Placeholder for the actual image content
        // In a real app, you would use Coil/Glide here:
        // AsyncImage(model = details.imageUrl, ...)
        Text(
            text = cardName,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Composable for a single key-value detail item (e.g., Set: Dominaria United).
 */
@Composable
fun CardPropertyItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Label (smaller, grayed out)
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant, // A muted color
            modifier = Modifier.padding(bottom = 4.dp)
        )
        // Value (main content)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        // A divider line similar to the screenshot, especially useful for long items like Rarity
        if (label == "Rarity" || label == "Set" || label == "Type") {
            Divider(
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 1.dp
            )
        }
    }
}

/**
 * Composable for the Bottom Navigation Bar.
 */
@Composable
fun DetailBottomBar() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        // Home Item
        NavigationBarItem(
            selected = false,
            onClick = { /* Navigate to Home */ },
            label = { Text("Home", style = MaterialTheme.typography.labelSmall) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") }
        )

        // Collection Item (Selected in the screenshot)
        NavigationBarItem(
            selected = true, // Set to true to match the screenshot
            onClick = { /* Navigate to Collection */ },
            label = { Text("Collection", style = MaterialTheme.typography.labelSmall) },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Collection") }
        )
    }
}

// --- Preview and Mock Data ---

@Preview(showBackground = true)
@Composable
fun CardDetailScreenPreview() {
    // Mock data based on the screenshot content
    val mockCard = CardDetails(
        name = "Donthus, The Nurturer",
        set = "Dominaria United",
        type = "Creature",
        rarity = "Rare",
        description = "A powerful creature with unique abilities, capable of turning the tide of battle."
    )

    // Using a custom Theme for the preview to ensure Material 3 styling
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF0077B6), // Example primary color
            onPrimary = Color.White,
            background = Color.White,
            surface = Color.White,
            onSurface = Color.Black,
            onSurfaceVariant = Color.Gray
        )
    ) {
        CardDetailScreen(details = mockCard)
    }
}