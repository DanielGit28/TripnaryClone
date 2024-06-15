package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.ColaboradoresDao
import com.app.tripnary.data.database.entities.ColaboradoresEntity
import com.app.tripnary.data.database.entities.LugaresRecomendadosEntity
import kotlinx.coroutines.flow.Flow

class DatabaseColaboradoresDataSource(private val colaboradoresDao: ColaboradoresDao) {

    suspend fun clearColaboradores() {
        colaboradoresDao.clearColaboradores()
    }
    fun getAllColaboradores(): Flow<List<ColaboradoresEntity>> = colaboradoresDao.getColaboradores()
    suspend fun insertColaboradores(colaboradores: List<ColaboradoresEntity>) {
        colaboradoresDao.insertAll(colaboradores)
    }

    suspend fun addColaborador(colaboradoresEntity: ColaboradoresEntity) : Long =
        colaboradoresDao.insert(colaboradoresEntity)
}