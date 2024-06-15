package com.app.tripnary.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    //const val baseURL = "https://app-tripnary-cms.vercel.app"
    const val baseURL = "http://192.168.0.12:3000"
    private const val apiURL = "$baseURL/api/"

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(70, TimeUnit.SECONDS)
        .writeTimeout(70, TimeUnit.SECONDS)
        .readTimeout(70, TimeUnit.SECONDS)
        .callTimeout(70, TimeUnit.SECONDS)
        .build()
    private val retrofit =
        Retrofit.Builder()
            .baseUrl(apiURL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun<T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}