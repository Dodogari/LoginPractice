package com.example.week9practice

import com.google.android.gms.common.api.Api.Client
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherObject {
    private fun getRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getRetroifitService(): WeatherInterface = getRetrofit().create(WeatherInterface::class.java)
}