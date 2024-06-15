package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.LugarPlanesRepository
import com.app.tripnary.domain.repositories.PlanViajeRepository

class DeleteLugarPlanUseCase  (private val lugarPlanesRepository: LugarPlanesRepository) {

    suspend fun execute(reference:String) : LugarPlanModel {
        var planViaje = lugarPlanesRepository.deleteLugarPlan(reference)
        return  planViaje
    }
}