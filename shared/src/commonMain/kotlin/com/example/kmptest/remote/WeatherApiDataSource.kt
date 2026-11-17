package com.example.kmptest.remote

import com.example.kmptest.di.apiModule
import com.example.kmptest.model.Weather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

suspend fun main() {
    startKoin { modules(apiModule) }
    val dataSource = GlobalContext.get().get<WeatherApiDataSource>()
    println("--- Météo via Flow (Vent < 10 m/s) ---")
    dataSource.loadWeathersWithFlow("Toulouse", "Nice", "Paris", "Lyon")
        .filter { it.speed < 10.0 }
        .collect { weather ->
            println("Ville : ${weather.name} | Température : ${weather.temp}°C | Vent : ${weather.speed} m/s")
        }
}

class WeatherApiDataSource(val client: HttpClient) {
    companion object {
        private const val API_URL = "https://api.openweathermap.org/data/2.5/find"
    }

    suspend fun loadWeathers(cityName: String): List<Weather> {
        val response = client.get(API_URL) {
            parameter("appid", API_KEY)
            parameter("units", "metric")
            parameter("lang", "fr")
            parameter("q", cityName)
        }
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        val resultDTO = response.body<WeatherResultDTO>()

        val list = resultDTO.list.map { it.toWeather() }
        list.forEach { weather ->
            if (weather.icon.isNotBlank()) {
                weather.icon = "https://openweathermap.org/img/wn/${weather.icon}@4x.png"
            }
        }
        return list
    }

    fun loadWeathersWithFlow(vararg cities: String) = flow<Weather> {
        cities.forEach { city ->
            val list = loadWeathers(city)
            list.forEach { weather ->
                emit(weather)
            }
            delay(1000)
        }
    }
}

@Serializable
data class WeatherResultDTO(
    val list: List<WeatherBeanDTO> = emptyList()
)

@Serializable
data class WeatherBeanDTO(
    val id: Int = 0,
    val name: String = "",
    val main: MainDTO? = null,
    val wind: WindDTO? = null,
    val weather: List<WeatherDescriptionDTO> = emptyList()
)

@Serializable
data class MainDTO(
    val temp: Double = 0.0
)

@Serializable
data class WindDTO(
    val speed: Double = 0.0
)

@Serializable
data class WeatherDescriptionDTO(
    val description: String = "", val icon: String = ""
)

fun WeatherBeanDTO.toWeather() = Weather(
    id = id,
    name = name,
    temp = main?.temp ?: 0.0,
    speed = wind?.speed ?: 0.0,
    description = weather.firstOrNull()?.description ?: "",
    icon = weather.firstOrNull()?.icon ?: ""
)
