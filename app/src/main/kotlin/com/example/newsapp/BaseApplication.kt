package com.example.newsapp

import android.content.Context

import com.example.newsapp.utils.network.NetworkClientFactory
import okhttp3.OkHttpClient

class BaseApplication : BuildTypeApplication() {
    private lateinit var client: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        client = NetworkClientFactory().createClient()
    }

    companion object {
        fun getOkHttpClient(context: Context): OkHttpClient {
            return (context.applicationContext as BaseApplication).client
        }
    }
}
