package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.repositories.PlanDiasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListDiasPlanesUseCase (private val diasRepository: PlanDiasRepository){

    fun execute(): Flow<List<PlanDiasModel>> =
        diasRepository.getAllDias()
            .map { dias -> dias.sortedBy { dias -> dias.reference } }
}