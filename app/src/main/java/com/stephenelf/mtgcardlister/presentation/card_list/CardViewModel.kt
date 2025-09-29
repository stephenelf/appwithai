package com.stephenelf.mtgcardlister.presentation.card_list

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephenelf.mtgcardlister.domain.usecase.GetCardsUseCase
import com.stephenelf.mtgcardlister.domain.usecase.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel for the card list screen.
// It uses Hilt for dependency injection to get the GetCardsUseCase.
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class CardListViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
) : ViewModel() {

    // Mutable state for the UI, exposed as an immutable State
    private val _state = MutableStateFlow(CardListState())
    val state = _state.asStateFlow()

    // State for the search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery=_searchQuery.asStateFlow()

    // Job for debouncing search queries
    private var searchJob: Job? = null

    init {
        // Fetch initial cards when the ViewModel is created
        getCards()
    }

    /**
     * Fetches cards from the API based on the current search query.
     * Uses a Flow from the use case to observe loading, success, and error states.
     */
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getCards(query: String = _searchQuery.value) {
        getCardsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        cards = result.data ?: emptyList(),
                        isLoading = false,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, error = "")
                }
            }
        }.launchIn(viewModelScope) // Launch the flow in the ViewModel's scope
    }

    /**
     * Updates the search query and triggers a debounced search.
     * A debounce mechanism prevents excessive API calls while the user is typing.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        searchJob?.cancel() // Cancel any previous search job
        searchJob = viewModelScope.launch {
            delay(500L) // Debounce delay
            getCards(query) // Trigger new search after delay
        }
    }
}