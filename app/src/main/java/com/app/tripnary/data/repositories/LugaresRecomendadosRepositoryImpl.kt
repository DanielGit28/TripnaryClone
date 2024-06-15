package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.LugaresRecomendadosServiceImpl
import com.app.tripnary.data.datasources.DatabaseLugaresRecomendadosDataSource
import com.app.tripnary.data.mappers.LugarPlanesMapper.lugarPlanEntityFromModel
import com.app.tripnary.data.mappers.LugarPlanesMapper.toLugarPlanEntityList
import com.app.tripnary.data.mappers.LugarPlanesMapper.toLugarPlanModelList
import com.app.tripnary.data.mappers.LugaresRecomendadosMapper.noteEntityFromModel
import com.app.tripnary.data.mappers.LugaresRecomendadosMapper.toLugaresRecomendadosEntityList
import com.app.tripnary.data.mappers.LugaresRecomendadosMapper.toLugaresRecomendadosModelList
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LugaresRecomendadosRepositoryImpl(private val lugaresRecomendadosDataSource: DatabaseLugaresRecomendadosDataSource) : LugaresRecomendadosRepository {

    private val lugaresRecomendadosService = LugaresRecomendadosServiceImpl()
    override suspend fun getLugaresRecomendadosById(reference: String): LugaresRecomendadosModel {
        return suspendCoroutine { continuation ->
            lugaresRecomendadosService.getLugaresRecomendadosById(reference) { lugares, error ->
                if (error != null) {
                    continuation.resumeWithException(error as Throwable)
                } else if (lugares != null) {

                    continuation.resume(lugares)
                } else {
                    continuation.resumeWithException(NullPointerException("Lugares is null"))
                }
            }
        }
    }

    override suspend fun getLugaresRecomendadosByIntereses(reference: String): List<LugaresRecomendadosModel> {
        return suspendCoroutine { continuation ->
            lugaresRecomendadosService.getLugaresRecomendadosByIntereses(reference) { lugares, error ->
                if (error != null) {
                    continuation.resumeWithException(error as Throwable)
                } else if (lugares != null) {

                    continuation.resume(lugares)
                } else {
                    continuation.resumeWithException(NullPointerException("Lugares is null"))
                }
            }
        }
    }

    override suspend fun getLugaresRecomendadosByPopularidad(): List<LugaresRecomendadosModel> {
        return suspendCoroutine { continuation ->
            lugaresRecomendadosService.getLugaresRecomendadosByPopularidad { lugares, error ->
                if (error != null) {
                    continuation.resumeWithException(error as Throwable)
                } else if (lugares != null) {

                    continuation.resume(lugares)
                } else {
                    continuation.resumeWithException(NullPointerException("Lugares is null"))
                }
            }
        }
    }

    override fun getAllLugares(): Flow<List<LugaresRecomendadosModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun addLugarPropio(lugaresRecomendadosModel: LugaresRecomendadosModel): LugaresRecomendadosModel {
        return suspendCoroutine { continuation ->
            val callback: (LugaresRecomendadosModel?) -> Unit = { lugarPropio ->
                if (lugarPropio != null) {

                    continuation.resume(lugarPropio)
                } else {
                    continuation.resumeWithException(Throwable("Error al actualizar el lugar"))
                }
            }

            lugaresRecomendadosService.addLugarPropio(lugaresRecomendadosModel, callback)
        }
    }


    override fun getAllPlanesLugaresByRecomendados(idPlanViajeLugarRecomendado: String): Flow<List<LugaresRecomendadosModel>> {
        lugaresRecomendadosService.getAllLugarPlanByRecomendados(idPlanViajeLugarRecomendado) { lugares ->
            if (lugares != null) {

                CoroutineScope(Dispatchers.IO).launch {
                    lugaresRecomendadosDataSource.clearLugaresRecomendados()
                    lugaresRecomendadosDataSource.insertLugaresRecomendados(lugares.toLugaresRecomendadosEntityList())
                }

            } else {

            }

        }

        return lugaresRecomendadosDataSource.getAllLugaresRecomendados()
            .map { planes -> planes.toLugaresRecomendadosModelList() }

    }

}