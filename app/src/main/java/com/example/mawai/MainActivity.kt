package com.example.mawai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mawai.ui.theme.MawaiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MawaiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /* TODO: Navigate to Pen State */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Key in Pen State")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* TODO: Navigate to Milking Data */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Key in Milking Data")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* TODO: Navigate to Report */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Show Report")
        }
    }
}
