package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.PaisModel
import kotlinx.coroutines.flow.Flow

interface ContinenteRepository {

    fun getAllContinentes(): Flow<List<ContinenteModel>>
}