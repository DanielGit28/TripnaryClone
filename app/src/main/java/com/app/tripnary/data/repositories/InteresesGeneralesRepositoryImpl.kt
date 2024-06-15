package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.InteresesGeneralesServiceImpl
import com.app.tripnary.data.datasources.DatabaseInteresesGeneralesDataSource
import com.app.tripnary.data.datasources.DatabaseUsuariosDataSource
import com.app.tripnary.data.mappers.InteresesGeneralesMapper.noteEntityFromModel
import com.app.tripnary.data.mappers.UsuariosMapper.noteEntityFromModel
import com.app.tripnary.domain.models.InteresesGeneralesModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.repositories.InteresesGeneralesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InteresesGeneralesRepositoryImpl (private val interesesGeneralesDataSource: DatabaseInteresesGeneralesDataSource, private val usuariosDataSource: DatabaseUsuariosDataSource)
    : InteresesGeneralesRepository
{
    private val interesesGeneralesService = InteresesGeneralesServiceImpl()
    override suspend fun addInteresesGenerales(interesesGenerales: InteresesGeneralesModel) {
        val callback: (UsuariosModel?) -> Unit = { newUsuario ->
            if (newUsuario != null){
                CoroutineScope(Dispatchers.IO).launch {
                    usuariosDataSource.clearTasks()
                    usuariosDataSource.addUsuario(newUsuario.noteEntityFromModel())
                }
            } else {

            }
        }

        interesesGeneralesService.addInteresesGenerales(interesesGenerales, callback)
    }

}