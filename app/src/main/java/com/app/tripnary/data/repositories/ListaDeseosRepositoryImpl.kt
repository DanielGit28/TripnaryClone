package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.ListaDeseosServiceImpl
import com.app.tripnary.data.mappers.LugarPlanesMapper.lugarPlanEntityFromModel
import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.repositories.ListaDeseosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ListaDeseosRepositoryImpl : ListaDeseosRepository {
    private val listaDeseosServiceImpl = ListaDeseosServiceImpl()

    override fun getAllListaDeseos(referenceUsuario:String?): Flow<List<ListaDeseosModel>> = flow {
        val listaDeseosModels = suspendCoroutine<List<ListaDeseosModel>?> { continuation ->
            listaDeseosServiceImpl.getAllListaDeseos ({ deseos ->
                continuation.resume(deseos)
            }, referenceUsuario)
        }

        if (listaDeseosModels != null) {
            emit(listaDeseosModels)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addListaDeseos(listaDeseosModel: ListaDeseosModel): ListaDeseosModel {
        return suspendCoroutine { continuation ->

            val callback: (ListaDeseosModel?) -> Unit = { listaDeseos ->
                if (listaDeseos != null) {
                    continuation.resume(listaDeseos)
                } else {
                    continuation.resumeWithException(Throwable("Error al actualizar el plan"))
                }
            }

            val result = listaDeseosServiceImpl.addListaDeseos(listaDeseosModel, callback)
        }
    }

    override suspend fun deleteListaDeseos(listaDeseosModel: ListaDeseosModel): ListaDeseosModel {
        return suspendCoroutine { continuation ->

            val callback: (ListaDeseosModel?) -> Unit = { listaDeseos ->
                if (listaDeseos != null) {
                    continuation.resume(listaDeseos)
                } else {
                    continuation.resumeWithException(Throwable("Error al actualizar el plan"))
                }
            }

            val result = listaDeseosServiceImpl.deleteListaDeseos(listaDeseosModel, callback)
        }
    }
}