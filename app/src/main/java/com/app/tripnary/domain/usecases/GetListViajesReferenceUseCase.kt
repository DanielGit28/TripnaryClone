package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.PlanViajeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListViajesReferenceUseCase (private val planViajeRepository: PlanViajeRepository){

    fun execute(reference: String): Flow<List<PlanViajeModel>> =
        planViajeRepository.getAllPlanesViajesByReference(reference)
            .map { plan -> plan.sortedBy { plan -> plan.reference } }
}