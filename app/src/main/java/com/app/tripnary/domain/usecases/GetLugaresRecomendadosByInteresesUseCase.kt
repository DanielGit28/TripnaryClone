package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosRepository

class GetLugaresRecomendadosByInteresesUseCase(private val lugaresRecomendadosRepository: LugaresRecomendadosRepository) {

    suspend fun execute(reference: String) : List<LugaresRecomendadosModel> {
        return lugaresRecomendadosRepository.getLugaresRecomendadosByIntereses(reference)
    }
}