package com.example.mawai.model

data class PenState(
    val date: String,
    val barn: String,
    val totalWeight: Float,
    val weightA: Float,
    val weightB: Float,
    val weightC: Float,
    val percentA: Float,
    val percentB: Float,
    val percentAB: Float
)
