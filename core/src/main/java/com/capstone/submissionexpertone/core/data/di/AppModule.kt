package com.capstone.submissionexpertone.core.data.di

import android.content.Context
import androidx.room.Room
import com.capstone.submissionexpertone.core.data.repository.MovieRepository
import com.capstone.submissionexpertone.core.data.source.local.LocalDataSource
import com.capstone.submissionexpertone.core.data.source.local.room.MovieDao
import com.capstone.submissionexpertone.core.data.source.local.room.MovieDatabase
import com.capstone.submissionexpertone.core.data.source.remote.RemoteDataSource
import com.capstone.submissionexpertone.core.data.source.remote.network.ApiService
import com.capstone.submissionexpertone.core.domain.repository.IMovieRepository
import com.capstone.submissionexpertone.core.domain.usecase.MovieInteractor
import com.capstone.submissionexpertone.core.domain.usecase.MovieUseCase
import com.capstone.submissionexpertone.core.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "movie.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase) = database.movieDao()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(movieDao: MovieDao) = LocalDataSource(movieDao)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService) = RemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideAppExecutors() = AppExecutors()

    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        appExecutors: AppExecutors
    ): IMovieRepository {  // Ubah return type menjadi IMovieRepository
        return MovieRepository(remoteDataSource, localDataSource, appExecutors)
    }

    @Provides
    @Singleton
    fun provideMovieUseCase(repository: IMovieRepository): MovieUseCase {
        return MovieInteractor(repository)
    }
}