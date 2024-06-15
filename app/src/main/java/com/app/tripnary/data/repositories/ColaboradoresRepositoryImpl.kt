package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.ColaboradoresServiceImpl
import com.app.tripnary.data.datasources.DatabaseColaboradoresDataSource
import com.app.tripnary.data.mappers.ColaboradoresMapper.noteEntityFromModel
import com.app.tripnary.data.mappers.ColaboradoresMapper.toColaboradoresEntityList
import com.app.tripnary.data.mappers.ColaboradoresMapper.toColaboradoresModelList
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.repositories.ColaboradoresRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ColaboradoresRepositoryImpl(private val colaboradoresDataSource: DatabaseColaboradoresDataSource) : ColaboradoresRepository {
    private val colaboradoresService = ColaboradoresServiceImpl()

    override suspend fun getColaboradoresByViaje(idPlanViaje: String): Flow<List<ColaboradoresModel>> {
        colaboradoresService.getColaboradoresByViaje(idPlanViaje) { colaboradores, error ->
            if (error != null) {

            } else if (colaboradores != null){
                CoroutineScope(Dispatchers.IO).launch{
                    colaboradoresDataSource.clearColaboradores()
                    colaboradoresDataSource.insertColaboradores(colaboradores.toColaboradoresEntityList())
                }
            } else {

            }
        }
        return colaboradoresDataSource.getAllColaboradores()
            .map { colaboradores -> colaboradores.toColaboradoresModelList() }
//        return suspendCoroutine { continuation ->
//            colaboradoresService.getColaboradoresByViaje(idPlanViaje) { colaboradores, error ->
//                if (error != null) {
//                    continuation.resumeWithException(error as Throwable)
//                } else if (colaboradores != null) {
//                    CoroutineScope(Dispatchers.IO).launch {
//                        colaboradoresDataSource.clearColaboradores()
//                        colaboradoresDataSource.insertColaboradores(colaboradores.toColaboradoresEntityList())
//                    }
//                    continuation.resume(colaboradores)
//                } else {
//                    continuation.resumeWithException(NullPointerException("List is null"))
//                }
//            }
//        }
    }

    override suspend fun addColaboradores(colaboradoresModel: ColaboradoresModel) {
        val callback: (ColaboradoresModel?) -> Unit = { newColaborador ->
            if (newColaborador != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    colaboradoresDataSource.clearColaboradores()
                    colaboradoresDataSource.addColaborador(newColaborador.noteEntityFromModel())
                }
            }
        }

        colaboradoresService.addColaboradores(colaboradoresModel, callback)
    }

    override suspend fun getColaboradoresByReference(reference: String): ColaboradoresModel {
        return suspendCoroutine { continuation ->
            colaboradoresService.getColaboradoresByReference(reference) { colaborador, error ->
                if (error != null) {
                    continuation.resumeWithException(error as Throwable)
                } else if (colaborador != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        colaboradoresDataSource.clearColaboradores()
                        colaboradoresDataSource.addColaborador(colaborador.noteEntityFromModel())
                    }
                    continuation.resume(colaborador)
                } else {
                    continuation.resumeWithException(NullPointerException("Colaborador is null"))
                }
            }
        }
    }

    override suspend fun updateColaborador(colaboradoresModel: ColaboradoresModel) {
        val callback: (ColaboradoresModel?) -> Unit = { updatedColaborador ->
            if (updatedColaborador != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    colaboradoresDataSource.clearColaboradores()
                    colaboradoresDataSource.addColaborador(updatedColaborador.noteEntityFromModel())
                }
            }

        }
        colaboradoresService.updateColaborador(colaboradoresModel, callback)
    }
}