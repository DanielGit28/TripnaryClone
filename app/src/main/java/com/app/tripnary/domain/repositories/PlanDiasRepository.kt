package com.app.tripnary.domain.repositories


import com.app.tripnary.domain.models.PlanDiasModel
import kotlinx.coroutines.flow.Flow

interface PlanDiasRepository {

    fun getAllDias(): Flow<List<PlanDiasModel>>

    fun getDiasByIdPlanViaje(idPlanViaje: String?): Flow<List<PlanDiasModel>>
}