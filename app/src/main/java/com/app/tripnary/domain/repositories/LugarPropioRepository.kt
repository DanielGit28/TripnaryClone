package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import kotlinx.coroutines.flow.Flow

interface LugarPropioRepository {

    suspend fun addLugarPropio(lugarPropioModel: LugarPropioModel): LugarPropioModel

    fun getAllPlanesLugaresByPropios(idPlanViajeLugar: String): Flow<List<LugarPropioModel>>
}