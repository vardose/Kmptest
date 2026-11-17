package com.example.kmptest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptest.model.Weather
import com.example.kmptest.remote.WeatherApiDataSource
import com.example.kmptest.di.apiModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.core.context.GlobalContext

suspend fun main() {
    startKoin { modules(apiModule) }
    val viewModel = GlobalContext.get().get<MainViewModel>()
    viewModel.loadWeathers("Lyon")

    while (viewModel.runInProgress.value) {
        delay(500)
    }

    if (viewModel.errorMessage.value.isNotBlank()) {
        println("ErrorMessage : ${viewModel.errorMessage.value}")
    } else {
        println("List :\n" + viewModel.dataList.value.joinToString("\n\n") { it.getResume() })
    }
}

class MainViewModel(val weatherApiDataSource: WeatherApiDataSource) : ViewModel() {
    val dataList = MutableStateFlow(emptyList<Weather>())
    val runInProgress = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    fun loadWeathers(cityName: String) {
        runInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                errorMessage.value = ""

                if (cityName.length < 3) {
                    throw Exception("Le nom de la ville doit faire au moins 3 caractères")
                }

                dataList.value = weatherApiDataSource.loadWeathers(cityName)
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = e.message ?: "Une erreur est survenue"
            } finally {
                runInProgress.value = false
            }
        }
    }
}
