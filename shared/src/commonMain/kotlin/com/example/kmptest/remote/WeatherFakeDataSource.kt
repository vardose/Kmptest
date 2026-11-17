package com.example.kmptest.remote

import com.example.kmptest.model.Weather
import kotlinx.coroutines.delay

object WeatherFakeDataSource {
    suspend fun loadWeathers(cityName: String): List<Weather> {
        delay(1000)
        return listOf(
            Weather(1, cityName, 20.0, 5.0, "Ensoleillé", "01d"),
            Weather(2, "$cityName (Sub)", 15.0, 8.0, "Nuageux", "02d")
        )
    }
}
