package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.PlanViajeRepository

class UpdatePlanViajeUseCase (private val repository: PlanViajeRepository) {

    suspend fun execute(planViajeModel: PlanViajeModel) : PlanViajeModel {
        return repository.updatePlanViaje(planViajeModel)
    }

}