package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.UsuariosServiceImpl
import com.app.tripnary.data.datasources.DatabaseUsuariosDataSource
import com.app.tripnary.data.mappers.UsuariosMapper.noteEntityFromModel

import com.app.tripnary.data.mappers.UsuariosMapper.toUsuariosEntityList
import com.app.tripnary.data.mappers.UsuariosMapper.toUsuariosModelList
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.repositories.UsuariosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UsuariosRepositoryImpl(private val usuariosDataSource: DatabaseUsuariosDataSource) : UsuariosRepository {
    private val usuariosService = UsuariosServiceImpl()
    override suspend fun addUsuario(usuariosModel: UsuariosModel): UsuariosModel {
        return suspendCoroutine { continuation ->
            val callback: (UsuariosModel?) -> Unit = { usuario ->
                if (usuario != null) {

                    continuation.resume(usuario)
                } else {
                    continuation.resumeWithException(Throwable("Error al agregar el usuario"))
                }
            }

            usuariosService.addUsuarios(usuariosModel, callback)
        }
    }

    override fun getAllUsers(): Flow<List<UsuariosModel>> {
        usuariosService.insertAllUsuarios { usuariosModels ->
            if (usuariosModels != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    usuariosDataSource.clearTasks()
                    usuariosDataSource.insertUsuarios(usuariosModels.toUsuariosEntityList())
                }

            } else {

            }
        }
        return usuariosDataSource.getAllUsuarios()
            .map { usuarios -> usuarios.toUsuariosModelList() }
    }

    override fun getAllUsersLocal(): Flow<List<UsuariosModel>> {

        return usuariosDataSource.getAllUsuarios()
            .map { usuarios -> usuarios.toUsuariosModelList() }
    }

    override suspend fun updateUsuarios(usuariosModel: UsuariosModel) {
        val callback: (UsuariosModel?) -> Unit = { newUsuario ->
            if (newUsuario != null){
                CoroutineScope(Dispatchers.IO).launch {
                    usuariosDataSource.clearTasks()
                    usuariosDataSource.addUsuario(newUsuario.noteEntityFromModel())
                }
            } else {

            }
        }
        usuariosService.updateUsuario(usuariosModel, callback)
    }

    override suspend fun getUserByEmail(correoElectronico: String): List<UsuariosModel> {
        return suspendCoroutine { continuation ->
            usuariosService.getUsuarioByEmail(correoElectronico) { usuario, error ->
                if (error != null) {
                    continuation.resumeWithException(error as Throwable)
                } else if (usuario != null) {
                    continuation.resume(usuario)
                } else {
                    continuation.resumeWithException(NullPointerException("User is null"))
                }
            }
        }
    }


}


