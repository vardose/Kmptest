package com.example.kmptest.ui

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.kmptest.model.Weather
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<Weather>,
    onPictureClick: (Weather) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(listState) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val scrollDelta = event.changes.firstOrNull()?.scrollDelta ?: Offset.Zero
                        if (scrollDelta.y != 0f) {
                            coroutineScope.launch {
                                listState.scrollBy(scrollDelta.y * 50)
                            }
                            event.changes.forEach { it.consume() }
                        }
                    }
                }
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { weather ->
            Card(
                modifier = Modifier.width(250.dp),
                onClick = { onPictureClick(weather) }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (weather.icon.isNotBlank()) {
                        AsyncImage(
                            model = weather.icon,
                            contentDescription = weather.description,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Text(text = weather.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Température: ${weather.temp}°C")
                    Text(text = weather.description)
                }
            }
        }
    }
}
