package com.stephenelf.mtgcardlister.domain.repository

import com.stephenelf.mtgcardlister.domain.model.Card

// Interface for the Card Repository in the domain layer.
// This defines the contract for data operations related to cards,
// abstracting the data source from the business logic.
interface CardRepository {
    // Suspended function to get a list of cards.
    // It takes pagination parameters and returns a list of domain Card objects.
    suspend fun getCards(name: String?,page: Int, pageSize: Int): List<Card>
}