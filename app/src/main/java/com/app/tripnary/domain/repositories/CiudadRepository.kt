package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.PlanViajeModel
import kotlinx.coroutines.flow.Flow

interface CiudadRepository {

    fun getAllCiudades(): Flow<List<CiudadModel>>

    fun getAllCiudadesByPais(idPais: String): Flow<List<CiudadModel>>
}