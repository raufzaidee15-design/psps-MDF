package com.example.mawai

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mawai.model.PenState
import com.example.mawai.model.MilkingData
import java.io.File
import java.io.FileOutputStream

@Composable
fun ReportScreen() {
    val context = LocalContext.current
    val penEntries: List<PenState> = DataRepository.penStates
        .sortedWith(compareByDescending<PenState> { it.date }.thenBy { it.barn })

    val milkEntries: List<MilkingData> = DataRepository.milkingData
        .sortedWith(compareByDescending<MilkingData> { it.date }.thenBy { it.barn })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { generatePdfAndShare(context, penEntries, milkEntries) },
            modifier = Modifier.fillMaxWidth()
        ) {
