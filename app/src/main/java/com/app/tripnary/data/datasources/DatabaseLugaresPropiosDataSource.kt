package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.LugarPropioDao
import com.app.tripnary.data.database.daos.LugaresRecomendadosDao
import com.app.tripnary.data.database.entities.LugarPlanEntity
import com.app.tripnary.data.database.entities.LugarPropioEntity
import kotlinx.coroutines.flow.Flow

class DatabaseLugaresPropiosDataSource (private val lugarPropioDao: LugarPropioDao) {

    fun getAllLugarPropios(): Flow<List<LugarPropioEntity>> = lugarPropioDao.getLugaresPropios()

    suspend fun addLugarPropio(lugarPropioEntity: LugarPropioEntity):Long =
        lugarPropioDao.insert(lugarPropioEntity)

    suspend fun insertAllLugares(lugaresPropios: List<LugarPropioEntity>) {
        lugarPropioDao.insertAll(lugaresPropios)
    }

    suspend fun clearLugares() {
        lugarPropioDao.clearAll()
    }
}