package com.example.mawai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mawai.model.MilkingData
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MilkingScreen() {
    var barn by remember { mutableStateOf("1L") }
    var totalMilk by remember { mutableStateOf("") }

    val date = remember {
        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Milking Data Entry", style = MaterialTheme.typography.headlineSmall)

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

        // Milk input
        OutlinedTextField(
            value = totalMilk,
            onValueChange = { totalMilk = it },
            label = { Text("Total Milk (liters)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val milk = totalMilk.toFloatOrNull() ?: return@Button

                DataRepository.milkingData.add(
                    MilkingData(
                        date = date,
                        barn = barn,
                        totalMilk = milk
                    )
                )

                totalMilk = "" // reset after saving
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Entry")
        }
    }
}
