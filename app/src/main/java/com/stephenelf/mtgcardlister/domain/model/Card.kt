package com.stephenelf.mtgcardlister.domain.model

// Represents a Magic: The Gathering card in the domain layer.
// This model is a simplified version of the DTO, containing only the
// information relevant to the application's business logic and UI.
data class Card(
    val id: String, // Using multiverseid or UUID as a unique identifier
    val name: String,
    val type: String,
    val imageUrl: String?, // Nullable as some older cards might not have an image
    val text: String?,
    val rarity: String?,
    val set: String?,
    val artist: String?,
    val flavorText: String?,
)
