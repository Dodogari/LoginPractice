package com.example.week9practice

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.week9practice.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import androidx.annotation.RequiresApi
import com.example.week9practice.LoginActivity.Companion.EXTRA_NAME
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        //날짜 불러오기
        val today = LocalDate.now()
        val sendDateFormat = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        mainBinding.daysOfWeek.text = today.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy"))
        Log.d("setting", sendDateFormat)

        mainBinding.userName.text = intent.getStringExtra(EXTRA_NAME)
        //시간 불러오기
        val sendTimeFormat = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm"))
        Log.d("setting", sendTimeFormat)

        //날씨 설정
        setWeather(sendDateFormat, sendTimeFormat)
        setSun(sendDateFormat, sendTimeFormat)
    }

    //날씨 설정
    private fun setWeather(sendDateFormat: String, sendTimeFormat: String) {
        val call = WeatherObject.getRetroifitService().getWeather(10, 1, "JSON",
        "20230607", "0500", 60, 127)

        call.enqueue(object : retrofit2.Callback<ResponseWeather> {
            override fun onResponse(call: Call<ResponseWeather>, response: Response<ResponseWeather>) {
                if(response.isSuccessful) {
                    val it: List<Item> = response.body()!!.response.body.items.item

                    for (i in it.indices) {
                        Log.d("category", it[i].category)
                        when(it[i].category) {
                            //강수 형태
                            "PTY" -> {
                                //1,4면 비, 2,3이면 눈 아이콘 표시
                                when (it[i].fcstValue) {
                                    "1", "4" -> {
                                        mainBinding.weatherIcon.setAnimation(R.raw.rainy_icon)
                                        mainBinding.weatherIcon.playAnimation()
                                        mainBinding.background.setAnimation(R.raw.rainy_background)
                                        mainBinding.background.playAnimation()
                                    }
                                    "2", "3" -> {
                                        mainBinding.weatherIcon.setAnimation(R.raw.snow_icon)
                                        mainBinding.weatherIcon.playAnimation()
                                        mainBinding.background.setAnimation(R.raw.snow_background)
                                        mainBinding.background.playAnimation()
                                    }
                                }
                                //로그로 출력
                                Log.d("sky", "강수 형태: " + it[i].fcstValue)
                            }
                            //하늘
                            "SKY" -> {
                                when (it[i].fcstValue) {
                                    "1" -> {
                                        //맑음
                                        mainBinding.weatherIcon.setAnimation(R.raw.sunny_icon)
                                        mainBinding.weatherIcon.playAnimation()
                                        mainBinding.background.setAnimation(R.raw.sunny_background)
                                        mainBinding.background.playAnimation()
                                    }
                                    "3" -> {
                                        mainBinding.weatherIcon.setAnimation(R.raw.cloudy_icon)
                                        mainBinding.weatherIcon.playAnimation()
                                        mainBinding.background.setAnimation(R.raw.windy_background)
                                        mainBinding.background.playAnimation()
                                    }
                                    "4" -> {
                                        mainBinding.weatherIcon.setAnimation(R.raw.mist_icon)
                                        mainBinding.weatherIcon.playAnimation()
                                        mainBinding.background.setAnimation(R.raw.windy_background)
                                        mainBinding.background.playAnimation()
                                    }
                                }
                                Log.d("sky", "sky: " + it[i].fcstValue)
                            }
                            //기온
                            "TMP" -> {
                                mainBinding.temperature.text = it[i].fcstValue
                                Log.d("sky", "temp: " + it[i].fcstValue)
                            }
                            //풍속
                            "WSD" -> {
                                mainBinding.wind.text = "${it[i].fcstValue}m/s"
                                Log.d("sky", "wind: " + it[i].fcstValue)
                            }
                            //강수 확률
                            "POP" -> {
                                mainBinding.rainPercent.text = "${it[i].fcstValue}%"
                                Log.d("sky", "rainPercent: " + it[i].fcstValue)
                            }
                            else -> continue
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseWeather>, t: Throwable) {
                Toast.makeText(this@MainActivity, "다시 시도", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //일출, 일몰 시간 설정
    private fun setSun(sendDateFormat: String, sendTimeFormat: String) {
        val call = SunObject.getRetroifitService().getSun(sendDateFormat, "서울")

        call.enqueue(object : retrofit2.Callback<ResponseWeather> {
            override fun onResponse(
                call: Call<ResponseWeather>,
                response: Response<ResponseWeather>
            ) {
                if(response.isSuccessful) {
                    val it: List<Item> = response.body()!!.response.body.items.item

                    for(i in it.indices) {
                        if(it[i].sunrise != null) {
                            if(it[0].sunrise.toInt() < sendTimeFormat.toInt() && sendTimeFormat.toInt() < it[0].sunset.toInt()) {
                                mainBinding.sunType.text = "SunSet"
                                mainBinding.sunTime.text = it[0].sunset
                            }
                            else {
                                mainBinding.sunType.text = "SunRise"
                                mainBinding.sunTime.text = it[0].sunrise
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseWeather>, t: Throwable) {
                Toast.makeText(this@MainActivity, "다시 시도", Toast.LENGTH_SHORT).show()
            }

        })
    }
}