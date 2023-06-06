package com.example.week9practice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SunInterface {
    //요청 데이터
    @GET("${BuildConfig.ENDPOINT_GET_SUN}?serviceKey=${BuildConfig.API_KEY}")
    fun getSun(
        @Query("locdate") locdate: String,
        @Query("pageNo") location: String
    ): Call<ResponseWeather>
}