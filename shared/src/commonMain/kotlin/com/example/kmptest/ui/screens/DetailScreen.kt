package com.example.kmptest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmptest.model.Weather
import coil3.compose.AsyncImage

@Composable
fun DetailScreen(
    weather: Weather,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onBack) {
            Text("Retour")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = weather.name, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        if (weather.icon.isNotBlank()) {
            AsyncImage(
                model = weather.icon,
                contentDescription = weather.description,
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Température : ${weather.temp}°C", style = MaterialTheme.typography.titleLarge)
        Text(text = "Vitesse du vent : ${weather.speed} m/s", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Description : ${weather.description}", style = MaterialTheme.typography.bodyLarge)
    }
}
