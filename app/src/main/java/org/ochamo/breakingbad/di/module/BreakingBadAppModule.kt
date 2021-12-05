package org.ochamo.breakingbad.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.ochamo.breakingbad.data.local.BreakingBadLocalDB
import org.ochamo.breakingbad.data.local.LocalDbRepository
import org.ochamo.breakingbad.data.local.LocalDbRepositoryImpl
import org.ochamo.breakingbad.data.network.BreakingBadService
import org.ochamo.breakingbad.data.repository.BreakingBadRepository
import org.ochamo.breakingbad.data.repository.BreakingBadRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BreakingBadAppModule {

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

    @Singleton
    @Provides
    fun provideBreakingBadDB(@ApplicationContext context: Context): BreakingBadLocalDB {
        return Room.databaseBuilder(
            context.applicationContext,
            BreakingBadLocalDB::class.java,
            "BreakingBadLocal.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideLocalDBRepository(
        database: BreakingBadLocalDB,
        ioDispatcher: CoroutineDispatcher
    ): LocalDbRepository {
        return LocalDbRepositoryImpl(
            database.breakingBadDao(),
            ioDispatcher
        )
    }

}