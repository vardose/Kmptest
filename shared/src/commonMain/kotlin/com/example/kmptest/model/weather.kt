package com.example.kmptest.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    var id: Int,
    var name: String,
    var temp: Double,
    var speed: Double,
    var description: String,
    var icon: String
) {
    fun getResume(): String = """
        Il fait ${temp}° à $name (id=$id) avec un vent de $speed m/s
        -Description : $description
        -Icône : $icon
    """.trimIndent()
}
