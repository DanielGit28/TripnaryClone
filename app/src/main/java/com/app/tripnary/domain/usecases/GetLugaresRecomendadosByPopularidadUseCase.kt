package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosRepository

class GetLugaresRecomendadosByPopularidadUseCase(private val lugaresRecomendadosRepository: LugaresRecomendadosRepository) {

    suspend fun execute() : List<LugaresRecomendadosModel> {
        return lugaresRecomendadosRepository.getLugaresRecomendadosByPopularidad()
    }
}