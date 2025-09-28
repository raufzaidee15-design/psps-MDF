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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Pen State Entry", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        // Barn selection
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = barn,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Barn") },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                listOf("1L", "1R", "2L", "2R", "3L", "3R").forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            barn = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Inputs
        OutlinedTextField(
            value = totalWeight,
            onValueChange = { totalWeight = it },
            label = { Text("Total Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = weightA,
            onValueChange = { weightA = it },
            label = { Text("Partition A Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = weightB,
            onValueChange = { weightB = it },
            label = { Text("Partition B Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = weightC,
            onValueChange = { weightC = it },
            label = { Text("Partition C Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        // Result
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Result (Saved on: $date)")
                percentageA?.let { Text("Partition A: ${"%.2f".format(it)}%") }
                percentageB?.let { Text("Partition B: ${"%.2f".format(it)}%") }
                percentageAB?.let { Text("A + B: ${"%.2f".format(it)}%") }
            }
        }
    }
}
