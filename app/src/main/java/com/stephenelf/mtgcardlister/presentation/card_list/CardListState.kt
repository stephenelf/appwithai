package com.stephenelf.mtgcardlister.presentation.card_list

import com.stephenelf.mtgcardlister.domain.model.Card

// Data class representing the UI state for the card list screen.
// It includes the list of cards, loading status, and any error messages.
data class CardListState(
    val isLoading: Boolean = false, // True if data is currently being loaded
    val cards: List<Card> = emptyList(), // The list of cards to display
    val error: String = "" // Error message if something went wrong
)