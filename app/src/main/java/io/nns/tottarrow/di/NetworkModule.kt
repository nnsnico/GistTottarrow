package io.nns.tottarrow.di

import com.squareup.moshi.Moshi
import io.nns.tottarrow.infrastracture.api.GistApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideGithubApi(get()) }
    single(named("mockClient")) { (url: String) -> provideMockRetrofit(get(), url) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    return OkHttpClient.Builder().also {
        it.addInterceptor(httpLoggingInterceptor)
    }.build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.github.com/")
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(ApplicationJsonAdapterFactory)
                    .build()
            )
        )
        .build()

private fun provideMockRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(ApplicationJsonAdapterFactory)
                    .build()
            )
        )
        .build()

private fun provideGithubApi(retrofit: Retrofit): GistApi =
    retrofit.create(GistApi::class.java)


