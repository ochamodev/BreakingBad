package org.ochamo.breakingbad.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.ochamo.breakingbad.data.network.BreakingBadService
import org.ochamo.breakingbad.data.repository.BreakingBadRepository
import org.ochamo.breakingbad.data.repository.BreakingBadRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.breakingbadapi.com/api/")
            .client(httpClient.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideBreakingBadService(retrofit: Retrofit): BreakingBadService {
        return retrofit.create(BreakingBadService::class.java)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO;
    }

    @Singleton
    @Provides
    fun provideBreakingBadRepository(
        breakingBadService: BreakingBadService,
        ioDispatcher: CoroutineDispatcher
    ): BreakingBadRepository {
        return BreakingBadRepositoryImpl(
            breakingBadService,
            ioDispatcher
        )
    }

}