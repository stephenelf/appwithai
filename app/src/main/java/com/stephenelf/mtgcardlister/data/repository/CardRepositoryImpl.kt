package com.stephenelf.mtgcardlister.data.repository

import com.stephenelf.mtgcardlister.data.remote.CardRemoteDataSource
import com.stephenelf.mtgcardlister.domain.model.Card
import com.stephenelf.mtgcardlister.domain.repository.CardRepository
import javax.inject.Inject


// Implementation of the CardRepository interface.
// This class is part of the data layer and is responsible for coordinating
// data from various sources (e.g., remote API, local database).
class CardRepositoryImpl @Inject constructor(
    private val remoteDataSource: CardRemoteDataSource
) : CardRepository {
    /**
     * Fetches a list of cards from the remote data source and maps them to domain models.
     * @param name Optional card name to filter by.
     * @param page Page number for pagination.
     * @param pageSize Number of cards per page.
     * @return A list of [Card] domain models.
     */
    override suspend fun getCards(name: String?, page: Int, pageSize: Int): List<Card> {
        // Fetch DTOs from the remote source
        val cardDtos = remoteDataSource.getCards(page, pageSize)
        // Map DTOs to domain models before returning
        return cardDtos.map { it.toCard() }
    }

}