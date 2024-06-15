package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.LugaresRecomendadosDao
import com.app.tripnary.data.database.entities.LugaresRecomendadosEntity
import kotlinx.coroutines.flow.Flow

class DatabaseLugaresRecomendadosDataSource(private val lugaresRecomendadosDao: LugaresRecomendadosDao) {

    fun getAllLugaresRecomendados(): Flow<List<LugaresRecomendadosEntity>> = lugaresRecomendadosDao.getLugaresRecomendados()

    suspend fun insertLugaresRecomendados(lugares: List<LugaresRecomendadosEntity>) {
        lugaresRecomendadosDao.insertAll(lugares)
    }

    suspend fun addLugarRecomendado(lugaresRecomendadosEntity: LugaresRecomendadosEntity) : Long =
        lugaresRecomendadosDao.insert(lugaresRecomendadosEntity)

    suspend fun clearLugaresRecomendados() {
        lugaresRecomendadosDao.clearLugaresRecomendados()
    }
}