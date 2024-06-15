package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.ColaboradoresModel
import kotlinx.coroutines.flow.Flow

interface ColaboradoresRepository {

    suspend fun getColaboradoresByViaje(idPlanViaje: String): Flow<List<ColaboradoresModel>>

    suspend fun addColaboradores(colaboradoresModel: ColaboradoresModel)

    suspend fun getColaboradoresByReference(reference: String) : ColaboradoresModel

    suspend fun updateColaborador(colaboradoresModel: ColaboradoresModel)
}