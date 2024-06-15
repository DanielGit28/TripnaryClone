package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.UsuariosModel
import kotlinx.coroutines.flow.Flow

interface UsuariosRepository {

    suspend fun addUsuario(usuariosModel: UsuariosModel): UsuariosModel
    fun getAllUsers(): Flow<List<UsuariosModel>>

    fun getAllUsersLocal(): Flow<List<UsuariosModel>>

    suspend fun updateUsuarios(usuariosModel: UsuariosModel)

    suspend fun getUserByEmail(correoElectronico: String) : List<UsuariosModel>
}