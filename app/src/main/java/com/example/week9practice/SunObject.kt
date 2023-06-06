package com.example.week9practice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SunObject {
    private fun getRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.URL_SUN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getRetroifitService(): SunInterface
            = getRetrofit().create(SunInterface::class.java)
}