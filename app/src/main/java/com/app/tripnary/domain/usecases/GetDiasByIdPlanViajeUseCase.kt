package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.repositories.PlanDiasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDiasByIdPlanViajeUseCase (private val diasRepository: PlanDiasRepository){
    fun execute(idPlanViaje: String?): Flow<List<PlanDiasModel>> =
        diasRepository.getDiasByIdPlanViaje(idPlanViaje)
            .map { dias -> dias.sortedBy { dias -> dias.reference }
            }
}