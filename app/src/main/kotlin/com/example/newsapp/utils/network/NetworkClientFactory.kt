package com.example.newsapp.utils.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class NetworkClientFactory {
    fun createClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor({ message -> Timber.d(message) })
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
                .addNetworkInterceptor(BuildTypeNetworkInterceptor())
                .addInterceptor(interceptor)
                .build()
    }
}
