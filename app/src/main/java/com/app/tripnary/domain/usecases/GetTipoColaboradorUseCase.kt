package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.repositories.PlanesViajesColaboracionRepository

class GetTipoColaboradorUseCase(private val planesViajesColaboracionRepository: PlanesViajesColaboracionRepository) {

    suspend fun execute(idPlanViaje: String) : List<ColaboradoresModel> {
        return planesViajesColaboracionRepository.getTipoColaboradorByIdPlanViaje(idPlanViaje)
    }
}