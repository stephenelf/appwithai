package com.stephenelf.mtgcardlister.presentation.card_list.components


import android.R.attr.contentDescription
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.util.DebugLogger
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.stephenelf.mtgcardlister.domain.model.Card
import com.stephenelf.mtgcardlister.ui.theme.FilterChipBackground
import com.stephenelf.mtgcardlister.ui.theme.SearchBarBackground
import com.stephenelf.mtgcardlister.ui.theme.SurfaceDark
import com.stephenelf.mtgcardlister.ui.theme.TextLight

/**
 * Composable for displaying a "Featured Card" item.
 * This card has a larger image and a short description.
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeaturedCardItem(
    card: Card,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(280.dp) // Fixed width for featured cards
            .height(200.dp) // Fixed height
            .padding(end = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Card Image
            Log.d("KK","url1="+card.imageUrl)
            var data=card.imageUrl
            if(card.imageUrl == null)
                data="http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=129465&type=card"
            val sizeResolver = rememberConstraintsSizeResolver()

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data)
                    .size(sizeResolver) // Set the target size to load the image at.
                    .build()
            )
            Image(
                painter = rememberAsyncImagePainter(
                    model = card.imageUrl,
                    placeholder = null,
                    error = null
                ),
                contentDescription = card.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp) // Image takes up top portion
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color.Gray.copy(alpha = 0.2f)) // Placeholder background
            )


            Spacer(modifier = Modifier.height(8.dp))
            // Card Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = card.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = TextLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = card.flavorText?.ifEmpty { card.text } ?:"" , // Use flavor text if available, otherwise regular text
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = TextLight.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Composable for displaying a "Latest Addition" card item in a grid.
 * This card has a square image and a title below it.
 */
@Composable
fun LatestAdditionCardItem(
    card: Card,
    modifier: Modifier = Modifier
) {
    Log.d("KK","LD url="+card.imageUrl)
    Card(
        modifier = modifier
            .width(160.dp) // Fixed width for latest additions
            .height(180.dp) // Fixed height
            .padding(4.dp), // Padding around each item in the grid
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Align content to top
        ) {
            // Card Image
            val imageLoader = LocalContext.current.imageLoader.newBuilder()
                .logger(DebugLogger())
                .build()
            Image(
                painter = rememberAsyncImagePainter(
                    model = card.imageUrl,
                    placeholder = null,
                    error = null,
                    imageLoader=imageLoader
                ),
                contentDescription = card.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp) // Square image portion
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color.Gray.copy(alpha = 0.2f)) // Placeholder background
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Card Name
            Text(
                text = card.name,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = TextLight,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

/**
 * Composable for the search bar and filter chips section.
 * This includes a search input field and two filter buttons ("Sets" and "Types").
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAndFilterSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)) // Rounded corners for search bar
                .background(SearchBarBackground),
            placeholder = { Text("Search", color = TextLight.copy(alpha = 0.6f)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = TextLight.copy(alpha = 0.8f)
                )
            },
            /*
            colors = TextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent, // No border on focus
                unfocusedContainerColor = Color.Transparent, // No border when unfocused
                cursorColor = TextLight
                //textColor = TextLight
            ),*/
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = false, // Implement selection logic if needed
                onClick = { /* Handle Sets filter click */ },
                label = { Text("Sets", color = TextLight) },
                shape = RoundedCornerShape(8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = FilterChipBackground,
                    labelColor = TextLight,
                    selectedContainerColor = MaterialTheme.colorScheme.secondary, // Example selected color
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                ),
                border = null // No border
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = false, // Implement selection logic if needed
                onClick = { /* Handle Types filter click */ },
                label = { Text("Types", color = TextLight) },
                shape = RoundedCornerShape(8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = FilterChipBackground,
                    labelColor = TextLight,
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                ),
                border = null
            )
            // Add more filter chips as needed
        }
    }
}
