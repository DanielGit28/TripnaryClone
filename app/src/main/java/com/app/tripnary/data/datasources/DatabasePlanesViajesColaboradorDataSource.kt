package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.PlanesViajesColaboradorDao
import com.app.tripnary.data.database.entities.PlanViajeEntity
import com.app.tripnary.data.database.entities.PlanesViajesColaboradorEntity
import kotlinx.coroutines.flow.Flow

class DatabasePlanesViajesColaboradorDataSource(private val planesViajesColaboradorDao: PlanesViajesColaboradorDao) {

    fun getAllPlanes(): Flow<List<PlanesViajesColaboradorEntity>> = planesViajesColaboradorDao.getPlanesViajes()

    suspend fun addPlanViaje(planEntity: PlanesViajesColaboradorEntity):Long =
        planesViajesColaboradorDao.insert(planEntity)

    suspend fun insertPlanes(planes: List<PlanesViajesColaboradorEntity>) {
        planesViajesColaboradorDao.insertAll(planes)
    }

    suspend fun clearPlanes() {
        planesViajesColaboradorDao.clearPlanes()
    }
}