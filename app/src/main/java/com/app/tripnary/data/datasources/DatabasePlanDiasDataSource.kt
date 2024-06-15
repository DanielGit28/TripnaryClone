package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.PlanDiasDao
import com.app.tripnary.data.database.daos.TaskDao
import com.app.tripnary.data.database.entities.PlanDiasEntity
import com.app.tripnary.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

class DatabasePlanDiasDataSource  (private val planDiasDao: PlanDiasDao) {
    fun getAllDias(): Flow<List<PlanDiasEntity>> = planDiasDao.getDias()

    suspend fun addDia(diasEntity: PlanDiasEntity):Long =
        planDiasDao.insert(diasEntity)

    suspend fun insertDias(dias: List<PlanDiasEntity>) {
        planDiasDao.insertAll(dias)
    }

    suspend fun clearDias() {
        planDiasDao.clearDias()
    }
}