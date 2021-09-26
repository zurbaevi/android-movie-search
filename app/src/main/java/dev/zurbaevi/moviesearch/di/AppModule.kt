package dev.zurbaevi.moviesearch.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.zurbaevi.moviesearch.BuildConfig
import dev.zurbaevi.moviesearch.data.Constants
import dev.zurbaevi.moviesearch.data.api.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor =
        Interceptor { chain ->
            val original = chain.request()
            val httpUrl = original.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(httpUrl)
            chain.proceed(requestBuilder.build())
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(header: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(header)
            }.build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

}