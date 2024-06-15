package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.repositories.PlanViajeRepository
import com.app.tripnary.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AddPlanViajeUseCase (private val repository: PlanViajeRepository) {

    suspend fun execute(planViajeModel: PlanViajeModel) : PlanViajeModel  {
      return repository.addPlanViaje(planViajeModel)
    }

}