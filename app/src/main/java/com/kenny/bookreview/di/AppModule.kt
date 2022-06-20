package com.kenny.bookreview.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kenny.bookreview.BuildConfig
import com.kenny.bookreview.data.remote.BookReviewApi
import com.kenny.bookreview.data.repository.FakeRepository
import com.kenny.bookreview.domain.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFunMarketingApi(): BookReviewApi {

        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                this.addInterceptor(interceptor = loggingInterceptor)
            }
        }.build()

        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(BookReviewApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        api: BookReviewApi
    ): BookRepository {
        return FakeRepository()
    }
}