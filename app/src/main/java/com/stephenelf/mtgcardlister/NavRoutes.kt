package com.stephenelf.mtgcardlister

object NavRoutes {
    const val CARD_LIST = "cardList"
    const val CARD_DETAILS = "cardDetails/{cardId}" // Example: pass cardId as an argument
    fun cardDetails(cardId: String) = "cardDetails/$cardId"
}