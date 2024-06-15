package com.app.tripnary.domain.usecases.lugaresRecomendadosAI


import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosAIRepository


class GetLugaresRecomendadosAIByInteresUseCase(private val lugaresRecomendadosAIRepository: LugaresRecomendadosAIRepository) {

    suspend fun execute(interes: InteresLugaresAIModel, referencePlanViaje: String, tipo: String) : String {
        return lugaresRecomendadosAIRepository.addPrompt(interes,referencePlanViaje,tipo)
    }
}