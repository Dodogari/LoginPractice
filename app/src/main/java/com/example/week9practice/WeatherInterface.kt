package com.example.week9practice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {
    //요청 데이터
    @GET("${BuildConfig.ENDPOINT_GET_WEATHER_FORECAST}?serviceKey=${BuildConfig.API_KEY}")
    fun getWeather(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String,
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    ): Call<ResponseWeather>
}