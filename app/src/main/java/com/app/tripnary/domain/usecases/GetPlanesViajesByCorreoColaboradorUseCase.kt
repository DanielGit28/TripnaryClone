package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.PlanesViajesColaboracionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPlanesViajesByCorreoColaboradorUseCase(private val planesViajesColaboracionRepository: PlanesViajesColaboracionRepository) {

    suspend fun execute(correo: String) : Flow<List<PlanViajeModel>> =
        planesViajesColaboracionRepository.getPlanesByCorreoColaborador(correo)
            .map { plan -> plan.sortedBy { plan -> plan.nombre } }

}