package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.LugarPlanesDao
import com.app.tripnary.data.database.daos.LugaresRecomendadosDao
import com.app.tripnary.data.database.entities.LugarPlanEntity
import com.app.tripnary.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

class DatabaseLugarPlanesDataSource (private val lugarPlanesDao: LugarPlanesDao) {

    fun getAllLugarPlanes(): Flow<List<LugarPlanEntity>> = lugarPlanesDao.getLugaresPlanes()

    suspend fun addLugarPlan(lugarPlanEntity: LugarPlanEntity):Long =
        lugarPlanesDao.insert(lugarPlanEntity)

    suspend fun insertAllLugares(lugaresPlanes: List<LugarPlanEntity>) {
        lugarPlanesDao.insertAll(lugaresPlanes)
    }

    suspend fun clearLugares() {
        lugarPlanesDao.clearAll()
    }
}