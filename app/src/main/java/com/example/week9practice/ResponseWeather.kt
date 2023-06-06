package com.example.week9practice

data class ResponseWeather(val response: StructWeatherResponse)
data class StructWeatherResponse(val header: Header, val body: WeatherBody)
data class Header(val resultCode: Int, val resultMsg: String)
data class WeatherBody(val dataType: String, val items: Items, val totalCount: Int)
data class Items(val item: List<Item>)
data class Item(val baseDate: String, val baseTime: String, val category: String,
                       val fcstDate : String, val fcstTime : String, val fcstValue : String, val nx: Int, val ny: Int,
                val sunrise: String,
                val suntransit: String,
                val sunset: String,
                val moonrise : String,
                val moontransit : String,
                val moonset : String)