package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.LugaresRecomendadosAIDao
import com.app.tripnary.data.database.entities.InteresesLugaresAIEntity
import kotlinx.coroutines.flow.Flow

class DatabaseLugaresRecomendadosAIDataSource(private val lugaresRecomendadosAIDao: LugaresRecomendadosAIDao) {

    fun getAllLugaresRecomendadosAI(): Flow<List<InteresesLugaresAIEntity>> = lugaresRecomendadosAIDao.getLugaresRecomendadosAI()

    suspend fun insertLugarRecomendadoAI(lugares: List<InteresesLugaresAIEntity>) {
        lugaresRecomendadosAIDao.insertAll(lugares)
    }

    suspend fun addLugarRecomendado(lugaresRecomendadosEntity: InteresesLugaresAIEntity) : Long =
        lugaresRecomendadosAIDao.insert(lugaresRecomendadosEntity)


    suspend fun clearLugaresRecomendados() {
        lugaresRecomendadosAIDao.clearLugaresRecomendadosAI()
    }

}