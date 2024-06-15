package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.UsuariosDao
import com.app.tripnary.data.database.entities.UsuariosEntity
import kotlinx.coroutines.flow.Flow

class DatabaseUsuariosDataSource(private val usuariosDao: UsuariosDao) {

    fun getAllUsuarios(): Flow<List<UsuariosEntity>> = usuariosDao.getUsuarios()

    suspend fun insertUsuarios(usuarios: List<UsuariosEntity>) {
        usuariosDao.insertAll(usuarios)
    }

    suspend fun addUsuario(usuariosEntity: UsuariosEntity) : Long =
        usuariosDao.insert(usuariosEntity)

    suspend fun clearTasks() {
        usuariosDao.clearTasks()
    }
}