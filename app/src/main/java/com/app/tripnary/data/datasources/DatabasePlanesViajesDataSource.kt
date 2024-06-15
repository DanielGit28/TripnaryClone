package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.PlanViajeDao
import com.app.tripnary.data.database.entities.PlanViajeEntity
import kotlinx.coroutines.flow.Flow

class DatabasePlanesViajesDataSource (private val planViajeDao: PlanViajeDao) {

    fun getAllPlanes(): Flow<List<PlanViajeEntity>> = planViajeDao.getPlanesViajes()

    suspend fun addPlanViaje(planEntity: PlanViajeEntity):Long =
        planViajeDao.insert(planEntity)

    suspend fun insertPlanes(planes: List<PlanViajeEntity>) {
        planViajeDao.insertAll(planes)
    }


    suspend fun clearPlanes() {
        planViajeDao.clearPlanes()
    }


}