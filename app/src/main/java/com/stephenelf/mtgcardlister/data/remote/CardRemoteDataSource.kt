package com.stephenelf.mtgcardlister.data.remote

import com.stephenelf.mtgcardlister.data.remote.dto.CardDto
import javax.inject.Inject

// Remote data source responsible for fetching card data from the API.
// It acts as an intermediary between the repository and the API service.
class CardRemoteDataSource @Inject constructor(
    private val apiService: CardApiService
) {
    // Fetches a list of cards from the API and maps them to domain models.
    suspend fun getCards(page: Int, pageSize: Int): List<CardDto> {
        return apiService.getCards(page, pageSize).cards}
    }

