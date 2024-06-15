package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.PlanViajeModel
import kotlinx.coroutines.flow.Flow

interface PlanesViajesColaboracionRepository {
    suspend fun getTipoColaboradorByIdPlanViaje(idPlanViaje: String) : List<ColaboradoresModel>
    suspend fun getPlanesByCorreoColaborador(correo: String): Flow<List<PlanViajeModel>>
}