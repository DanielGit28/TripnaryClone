package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import kotlinx.coroutines.flow.Flow

interface LugarPlanesRepository {

    suspend fun addLugarPlanes(lugarPlanModel: LugarPlanModel): LugarPlanModel

    fun getAllPlanesLugaresByReference(idPlanViaje: String): Flow<List<LugarPlanModel>>

    suspend fun updateLugarPlanes(lugarPlanModel: LugarPlanModel): LugarPlanModel

    suspend fun deleteLugarPlan(reference: String) : LugarPlanModel
}