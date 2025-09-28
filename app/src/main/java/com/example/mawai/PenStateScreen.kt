package com.example.mawai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PenStateScreen() {
    var barn by remember { mutableStateOf("1L") }
    var totalWeight by remember { mutableStateOf("") }
    var weightA by remember { mutableStateOf("") }
    var weightB by remember { mutableStateOf("") }
    var weightC by remember { mutableStateOf("") }

    val date = remember {
        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
    }

    val percentageA = totalWeight.toFloatOrNull()?.let { total ->
        weightA.toFloatOrNull()?.div(total)?.times(100)
    }
    val percentageB = totalWeight.toFloatOrNull()?.let { total ->
        weightB.toFloatOrNull()?.div(total)?.times(100)
    }
    val percentageAB = if (percentageA != null && percentageB != null) {
        percentageA + percentageB
    } else null

    Column(
      
