package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.LugarPlanesRepository
import com.app.tripnary.domain.repositories.PlanViajeRepository

class AddLugarPlanUseCase  (private val lugarPlanesRepository: LugarPlanesRepository) {

    suspend fun execute(lugarPlanModel: LugarPlanModel) : LugarPlanModel {
        var lugarPlan = lugarPlanesRepository.addLugarPlanes(lugarPlanModel)
        return lugarPlan
    }
}