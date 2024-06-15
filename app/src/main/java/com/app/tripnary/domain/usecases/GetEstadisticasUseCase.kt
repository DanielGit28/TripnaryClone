package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.EstadisticasModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.repositories.EstadisticasRepository
import com.app.tripnary.domain.repositories.LugarPlanesRepository

class GetEstadisticasUseCase  (private val estadisticasRepository: EstadisticasRepository) {

    suspend fun execute(reference:String) : EstadisticasModel {
        var estadisticas = estadisticasRepository.getEstadisticasUsuario(reference)
        return  estadisticas
    }
}