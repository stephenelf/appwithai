package com.stephenelf.mtgcardlister.di

import com.stephenelf.mtgcardlister.data.remote.CardApiService
import com.stephenelf.mtgcardlister.data.repository.CardRepositoryImpl
import com.stephenelf.mtgcardlister.domain.repository.CardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

/**
 * Hilt module for providing application-wide dependencies.
 * Annotated with [InstallIn(SingletonComponent::class)] to make dependencies available
 * throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.magicthegathering.io/v1/"

    /**
     * Provides an [OkHttpClient] instance.
     * Includes an [HttpLoggingInterceptor] for logging network requests and responses,
     * which is very useful for debugging.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
            })
            .build()
    }

    /**
     * Provides a [Retrofit] instance.
     * Configured with the base URL, [OkHttpClient], and [GsonConverterFactory] for JSON parsing.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides an instance of [MtgApi] using Retrofit.
     * This is the interface for our API service.
     */
    @Provides
    @Singleton
    fun provideMtgApi(retrofit: Retrofit): CardApiService {
        return retrofit.create(CardApiService::class.java)
    }

    /**
     * Provides the concrete implementation of [CardRepository].
     * Binds [CardRepositoryImpl] to the [CardRepository] interface,
     * allowing for easy swapping of implementations if needed (e.g., for testing or different data sources).
     */
    @Provides
    @Singleton
    fun provideCardRepository(repository: CardRepositoryImpl): CardRepository {
        return repository
    }
}