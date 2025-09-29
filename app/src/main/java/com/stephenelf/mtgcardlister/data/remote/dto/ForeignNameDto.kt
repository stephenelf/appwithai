package com.stephenelf.mtgcardlister.data.remote.dto

import com.google.gson.annotations.SerializedName

// Nested DTOs for various card properties
data class ForeignNameDto(
    val name: String?,
    val language: String?,
    val multiverseid: Int?
)

data class RulingDto(
    val date: String?,
    val text: String?
)

data class CardPartDto(
    val name: String?,
    val type: String?,
    val id: String?
)

data class LeadershipSkillsDto(
    val brawl: Boolean?,
    val commander: Boolean?,
    val oathbreaker: Boolean?
)

data class PricesDto(
    val paper: PaperPricesDto?,
    val mtgo: MtgoPricesDto?
)

data class PaperPricesDto(
    val foil: String?,
    val nonfoil: String?
)

data class MtgoPricesDto(
    val foil: String?,
    val nonfoil: String?
)

data class PurchaseUrlsDto(
    val tcgplayer: String?,
    val cardmarket: String?,
    val cardkingdom: String?
)

/**
 * Data Transfer Object (DTO) for the API response containing a list of cards.
 * The API wraps the list of cards under a "cards" key.
 */
data class CardResponseDto(
    @SerializedName("cards")
    val cards: List<CardDto>
)