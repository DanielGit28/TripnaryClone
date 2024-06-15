package com.app.tripnary.domain.usecases.lugaresRecomendadosAI

import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosAIRepository


class AddLugarRecomendadoAIUseCase  (private val lugarRecomendadosAIRepository: LugaresRecomendadosAIRepository) {

    suspend fun execute(lugarRecomendadoAIModel: InteresLugaresAIModel) : InteresLugaresAIModel {
        var lugarRecomendadoAI = lugarRecomendadosAIRepository.addLugarRecomendadoAI(lugarRecomendadoAIModel)
        return lugarRecomendadoAI
    }
}