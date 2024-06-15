package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.repositories.LugarPlanesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListLugarPlanByPlanViajeUseCase(private val lugarPlanesRepository: LugarPlanesRepository) {

    fun execute(idPlanViaje: String): Flow<List<LugarPlanModel>> =
        lugarPlanesRepository.getAllPlanesLugaresByReference(idPlanViaje)
            .map { plan ->
                plan.sortedBy { plan -> plan.reference }

            }
}