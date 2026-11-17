package com.example.kmptest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmptest.navigation.AppNavigation
import com.example.kmptest.ui.theme.KmptestTheme

@Composable
@Preview
fun App() {
    KmptestTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize().safeContentPadding()
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavigation()
            }
        }
    }
}
