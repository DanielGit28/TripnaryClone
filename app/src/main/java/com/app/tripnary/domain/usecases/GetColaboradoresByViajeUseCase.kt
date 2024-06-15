package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.repositories.ColaboradoresRepository
import kotlinx.coroutines.flow.Flow

class GetColaboradoresByViajeUseCase(private val colaboradoresRepository: ColaboradoresRepository) {

    suspend fun execute(idPlanViaje: String) : Flow<List<ColaboradoresModel>> {
        return colaboradoresRepository.getColaboradoresByViaje(idPlanViaje)
    }
}