package com.example.kmptest.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kmptest.model.Weather

@Composable
expect fun WeatherGallery(
    modifier: Modifier = Modifier,
    list: List<Weather>,
    onPictureClick: (Weather) -> Unit
)
