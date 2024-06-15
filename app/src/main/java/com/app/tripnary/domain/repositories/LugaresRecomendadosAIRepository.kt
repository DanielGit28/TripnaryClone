package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.InteresLugaresAIModel
import kotlinx.coroutines.flow.Flow

interface LugaresRecomendadosAIRepository {
    suspend fun getLugarRecomendadoAIById(reference: String) : InteresLugaresAIModel

    suspend fun getLugaresRecomendadosAIByInteres(reference: String) : List<InteresLugaresAIModel>


    fun getAllLugares(): Flow<List<InteresLugaresAIModel>>

    suspend fun addLugarRecomendadoAI(interesLugaresAIModel: InteresLugaresAIModel): InteresLugaresAIModel
    suspend fun addPrompt(interesLugaresAIModel: InteresLugaresAIModel, referencePlanViaje: String, tipo: String): String
}