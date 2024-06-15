package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import kotlinx.coroutines.flow.Flow

interface EquipajeRepository {

    fun getAllEquipajeByPlan(idPlanViaje: String): Flow<List<EquipajeModel>>

    suspend fun addEquipaje(equipajeModel: EquipajeModel) : EquipajeModel

    suspend fun updateEquipaje(equipajeModel: EquipajeModel) : EquipajeModel

    suspend fun deleteEquipaje(reference: String) : EquipajeModel
}