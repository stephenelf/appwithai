package com.stephenelf.mtgcardlister.data.remote

import com.stephenelf.mtgcardlister.data.remote.dto.CardResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit interface for the Magic: The Gathering API.
interface CardApiService {

    @GET("cards")
    suspend fun getCards(
        @Query("page") page: Int = 1, // Pagination support
        @Query("pageSize") pageSize: Int = 20 // Number of cards per page
    ): CardResponseDto

    // You can add more endpoints here for specific card searches, etc.
    // For example, to search by name:
    @GET("cards")
    suspend fun searchCardsByName(
        @Query("name") name: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): CardResponseDto
}