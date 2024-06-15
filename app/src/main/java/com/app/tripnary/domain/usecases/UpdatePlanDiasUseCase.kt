package com.app.tripnary.domain.usecases

import android.util.Log
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.repositories.PlanViajeRepository

class UpdatePlanDiasUseCase  (private val planViajeRepository: PlanViajeRepository) {

    suspend fun execute(reference:String,planViajeModel: PlanViajeModel) : PlanViajeModel{
        var planViaje = planViajeRepository.updateDiasPlanes(reference, planViajeModel)
        return  planViaje
    }
}