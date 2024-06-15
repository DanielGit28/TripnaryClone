package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.TaskModel
import kotlinx.coroutines.flow.Flow

interface PlanViajeRepository {

    suspend fun addPlanViaje(planViaje: PlanViajeModel) : PlanViajeModel

    fun getAllPlanesViajes(): Flow<List<PlanViajeModel>>

    fun getAllPlanesViajesByReference(reference: String): Flow<List<PlanViajeModel>>

    suspend fun updateDiasPlanes(reference: String,planViaje: PlanViajeModel): PlanViajeModel

    suspend fun updatePlanViaje(planViaje: PlanViajeModel) : PlanViajeModel

    suspend fun deletePlanViaje(reference: String) : PlanViajeModel
}