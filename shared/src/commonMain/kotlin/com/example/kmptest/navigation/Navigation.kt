package com.example.kmptest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import com.example.kmptest.MainViewModel
import com.example.kmptest.model.Weather
import com.example.kmptest.ui.screens.SearchScreen
import com.example.kmptest.ui.screens.DetailScreen
import org.koin.compose.viewmodel.koinViewModel

@Serializable
sealed interface Routes : NavKey {
    @Serializable
    data object SearchRoute : Routes

    @Serializable
    data class DetailRoute(val weather: Weather) : Routes
}

private val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Routes.SearchRoute::class, Routes.SearchRoute.serializer())
            subclass(Routes.DetailRoute::class, Routes.DetailRoute.serializer())
        }
    }
}

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(navConfig, Routes.SearchRoute)
    val viewModel: MainViewModel = koinViewModel()

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() }
    ) { key ->
        NavEntry(key) {
            when (key) {
                is Routes.SearchRoute -> {
                    SearchScreen(
                        viewModel = viewModel,
                        onWeatherClick = { weather ->
                            backStack.add(Routes.DetailRoute(weather))
                        }
                    )
                }
                is Routes.DetailRoute -> {
                    DetailScreen(
                        weather = key.weather,
                        onBack = { backStack.removeLastOrNull() }
                    )
                }
            }
        }
    }
}
