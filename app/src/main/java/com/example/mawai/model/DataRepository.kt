package com.example.mawai

import com.example.mawai.model.PenState
import com.example.mawai.model.MilkingData

object DataRepository {
    val penStates = mutableListOf<PenState>()
    val milkingData = mutableListOf<MilkingData>()
}
