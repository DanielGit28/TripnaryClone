package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PaisModel
import kotlinx.coroutines.flow.Flow

interface LugaresRecomendadosRepository {
    suspend fun getLugaresRecomendadosById(reference: String) : LugaresRecomendadosModel

    suspend fun getLugaresRecomendadosByIntereses(reference: String) : List<LugaresRecomendadosModel>

    suspend fun getLugaresRecomendadosByPopularidad() : List<LugaresRecomendadosModel>

    fun getAllLugares(): Flow<List<LugaresRecomendadosModel>>

    suspend fun addLugarPropio(lugaresRecomendadosModel: LugaresRecomendadosModel): LugaresRecomendadosModel

    fun getAllPlanesLugaresByRecomendados(idPlanViajeLugarRecomendado: String): Flow<List<LugaresRecomendadosModel>>
}