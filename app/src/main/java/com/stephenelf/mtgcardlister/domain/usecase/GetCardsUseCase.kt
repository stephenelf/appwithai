package com.stephenelf.mtgcardlister.domain.usecase

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.stephenelf.mtgcardlister.domain.model.Card
import com.stephenelf.mtgcardlister.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

// Use case responsible for fetching a list of cards.
// This class orchestrates the data flow from the repository to the ViewModel,
// handling potential errors and emitting different states (Loading, Success, Error).
class GetCardsUseCase @Inject constructor(
    private val repository: CardRepository
) {
    // Operator function to make the use case invokable like a function.
    // It returns a Flow of Resource, allowing the ViewModel to observe data states.
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(page: Int = 1, pageSize: Int = 20): Flow<Resource<List<Card>>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            val cards = repository.getCards(name="", page=page, pageSize=pageSize) // Fetch cards from the repository
            emit(Resource.Success(cards)) // Emit success state with the fetched cards
        } catch (e: HttpException) {
            // Handle HTTP errors (e.g., 404, 500)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            // Handle network errors (e.g., no internet connection)
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            // Catch any other unexpected errors
            emit(Resource.Error("An unknown error occurred: ${e.localizedMessage}"))
        }
    }
}

/**
 * A generic wrapper class for handling different states of data loading:
 * Loading, Success, and Error.
 * This helps in managing UI state based on the asynchronous operation's outcome.
 *
 * @param T The type of data being loaded.
 * @param data The actual data, present only in [Success] state.
 * @param message An optional message, typically for error states.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}