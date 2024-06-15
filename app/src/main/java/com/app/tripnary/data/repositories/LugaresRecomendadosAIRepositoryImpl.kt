package com.app.tripnary.data.repositories


import android.util.Log
import com.app.tripnary.data.api.servicesimpl.LugaresRecomendadosAIServiceImpl
import com.app.tripnary.data.datasources.DatabaseLugaresRecomendadosAIDataSource
import com.app.tripnary.data.mappers.LugaresRecomendadosAIMapper.noteEntityFromModel
import com.app.tripnary.data.mappers.LugaresRecomendadosAIMapper.toLugaresRecomendadosAIEntityList
import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosAIRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LugaresRecomendadosAIRepositoryImpl(private val lugaresRecomendadosAIDataSource: DatabaseLugaresRecomendadosAIDataSource) : LugaresRecomendadosAIRepository {

    private val lugaresRecomendadosService = LugaresRecomendadosAIServiceImpl()
    override suspend fun getLugarRecomendadoAIById(reference: String): InteresLugaresAIModel {
        return suspendCoroutine { continuation ->
            lugaresRecomendadosService.getLugarRecomendadoById(reference) { lugar, error ->
                if (error != null) {
                    continuation.resumeWithException(error as Throwable)
                } else if (lugar != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        lugaresRecomendadosAIDataSource.clearLugaresRecomendados()
                        lugaresRecomendadosAIDataSource.addLugarRecomendado(lugar.noteEntityFromModel())
                    }
                    continuation.resume(lugar)
                } else {
                    continuation.resumeWithException(NullPointerException("Lugar recomendado ai is null"))
                }
            }
        }
    }


    override suspend fun getLugaresRecomendadosAIByInteres(idInteres: String): List<InteresLugaresAIModel> {
        return suspendCoroutine { continuation ->
            lugaresRecomendadosService.getLugaresRecomendadosAIByInteres(idInteres) { lugares, error ->
                if (error != null) {
                    continuation.resumeWithException(error as Throwable)
                } else if (lugares != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        lugaresRecomendadosAIDataSource.clearLugaresRecomendados()
                        lugaresRecomendadosAIDataSource.insertLugarRecomendadoAI(lugares.toLugaresRecomendadosAIEntityList())
                    }
                    continuation.resume(lugares)
                } else {
                    continuation.resumeWithException(NullPointerException("Lugares is null"))
                }
            }
        }
    }

    override fun getAllLugares(): Flow<List<InteresLugaresAIModel>> = flow {
        val lugarPlanesModel = suspendCoroutine<List<InteresLugaresAIModel>?> { continuation ->
            lugaresRecomendadosService.getAllLugares() { lugares ->
                continuation.resume(lugares)
            }
        }

        if (lugarPlanesModel != null) {
            emit(lugarPlanesModel)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addLugarRecomendadoAI(interesLugaresAIModel: InteresLugaresAIModel): InteresLugaresAIModel {
        return suspendCoroutine { continuation ->
            val callback: (InteresLugaresAIModel?) -> Unit = { lugar ->
                if (lugar != null) {

                    continuation.resume(lugar)
                } else {
                    continuation.resumeWithException(Throwable("Error al actualizar el lugar recomendado ai"))
                }
            }

            lugaresRecomendadosService.addLugarRecomendaoAI(interesLugaresAIModel, callback)
        }
    }

    override suspend fun addPrompt(interesLugaresAIModel: InteresLugaresAIModel, referencePlanViaje: String, tipo: String): String {
        return suspendCoroutine { continuation ->
            val callback: (String) -> Unit = {recomendaciones ->
                if(recomendaciones != null) {

                    continuation.resume(recomendaciones)
                } else {
                    continuation.resumeWithException(Throwable("Error con obtener recomendaciones ai"))
                }

            }
            lugaresRecomendadosService.postPrompt(interesLugaresAIModel, referencePlanViaje, tipo, callback)
        }

    }


}