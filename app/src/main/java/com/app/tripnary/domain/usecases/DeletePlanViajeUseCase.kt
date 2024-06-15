package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.LugarPlanesRepository
import com.app.tripnary.domain.repositories.PlanViajeRepository

class DeletePlanViajeUseCase  (private val planViajeRepository: PlanViajeRepository) {

    suspend fun execute(reference:String) : PlanViajeModel {
        var planViaje = planViajeRepository.deletePlanViaje(reference)
        return  planViaje
    }
}