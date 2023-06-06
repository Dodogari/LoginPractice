package com.example.week9practice


data class ResponseSun (val response: StructSunResponse)
data class StructSunResponse(val header: Header, val body: SunBody)
data class SunBody(val dataType: String, val items: SunItems, val totalCount: Int)
data class SunItems(val item: List<Item>)
data class SunItem(val sunrise: String,
                   val suntransit: String,
                   val sunset: String,
                   val moonrise : String,
                   val moontransit : String,
                   val moonset : String)