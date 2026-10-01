package com.example.sumativa2seaa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.sumativa2seaa.components.PropinaScreen
import com.example.sumativa2seaa.ui.theme.Sumativa2SEAATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sumativa2SEAATheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PropinaScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}