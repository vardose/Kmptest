package com.example.kmptest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmptest.MainViewModel
import com.example.kmptest.model.Weather
import com.example.kmptest.ui.WeatherGallery

@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    onWeatherClick: (Weather) -> Unit
) {
    var cityName by remember { mutableStateOf("") }
    val dataList by viewModel.dataList.collectAsState()
    val runInProgress by viewModel.runInProgress.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Nom de la ville") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.loadWeathers(cityName) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Rechercher")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (runInProgress) {
            CircularProgressIndicator()
        }

        if (errorMessage.isNotBlank()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        WeatherGallery(
            list = dataList,
            onPictureClick = onWeatherClick
        )
    }
}
