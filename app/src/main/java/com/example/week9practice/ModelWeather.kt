package com.example.week9practice

import com.google.gson.annotations.SerializedName

data class ModelWeather (
    @SerializedName("rainType") var rainType: String = "",   //강수 형태
    @SerializedName("sky") var sky: String,                  //하늘 형태
    @SerializedName("temperature") var temperature: String,  //현재 기온
    @SerializedName("wind") var wind: String,                //풍속
    @SerializedName("rainPercent") var rainPercent: String   //강수 확률
)