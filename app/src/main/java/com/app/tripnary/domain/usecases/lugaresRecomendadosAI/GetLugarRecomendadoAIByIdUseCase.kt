package com.app.tripnary.domain.usecases.lugaresRecomendadosAI

import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosAIRepository


class GetLugarRecomendadoAIByIdUseCase(private val lugaresRecomendadosAIRepository: LugaresRecomendadosAIRepository) {

    suspend fun execute(reference: String) : InteresLugaresAIModel {
        return lugaresRecomendadosAIRepository.getLugarRecomendadoAIById(reference)
    }
}