package com.example.kmptest

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.kmptest.di.initKoin

fun main() = application {
    try {
        initKoin()
    } catch (_: Exception) {}

    Window(
        onCloseRequest = ::exitApplication,
        title = "Kmptest",
    ) {
        App()
    }
}
