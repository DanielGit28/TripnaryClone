package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosRepository

class GetLugaresRecomendadosByIdUseCase(private val lugaresRecomendadosRepository: LugaresRecomendadosRepository) {

    suspend fun execute(reference: String) : LugaresRecomendadosModel {
        return lugaresRecomendadosRepository.getLugaresRecomendadosById(reference)
    }
}