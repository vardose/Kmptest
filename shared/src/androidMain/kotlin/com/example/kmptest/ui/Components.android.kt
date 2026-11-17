package com.example.kmptest.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmptest.model.Weather
import coil3.compose.AsyncImage

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<Weather>,
    onPictureClick: (Weather) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { weather ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onPictureClick(weather) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (weather.icon.isNotBlank()) {
                        AsyncImage(
                            model = weather.icon,
                            contentDescription = weather.description,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Column {
                        Text(text = weather.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Température: ${weather.temp}°C")
                        Text(text = weather.description)
                    }
                }
            }
        }
    }
}
