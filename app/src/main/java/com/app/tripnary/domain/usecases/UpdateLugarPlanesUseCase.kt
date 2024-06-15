package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.repositories.LugarPlanesRepository

class UpdateLugarPlanesUseCase  (private val lugarPlanesRepository: LugarPlanesRepository) {

    suspend fun execute(lugarPlanModel: LugarPlanModel) : LugarPlanModel {
        var lugarPlan = lugarPlanesRepository.updateLugarPlanes(lugarPlanModel)
        return lugarPlan
    }
}