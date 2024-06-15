package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.LugaresRecomendadosServiceImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LugaresRecomendadosViajesRepositoryImpl : LugaresRecomendadosRepository {

    private val lugaresRecomendadosService = LugaresRecomendadosServiceImpl()

    override suspend fun getLugaresRecomendadosById(reference: String): LugaresRecomendadosModel {
        TODO("Not yet implemented")
    }

    override suspend fun getLugaresRecomendadosByIntereses(reference: String): List<LugaresRecomendadosModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getLugaresRecomendadosByPopularidad(): List<LugaresRecomendadosModel> {
        TODO("Not yet implemented")
    }

    override fun getAllLugares(): Flow<List<LugaresRecomendadosModel>> = flow {
        val lugaresModels = suspendCoroutine<List<LugaresRecomendadosModel>?> { continuation ->
            lugaresRecomendadosService.getAllLugares { lugares ->
                continuation.resume(lugares)
            }
        }

        if (lugaresModels != null) {
            emit(lugaresModels)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addLugarPropio(lugaresRecomendadosModel: LugaresRecomendadosModel): LugaresRecomendadosModel {
        TODO("Not yet implemented")
    }

    override fun getAllPlanesLugaresByRecomendados(idPlanViajeLugarRecomendado: String): Flow<List<LugaresRecomendadosModel>> {
        TODO("Not yet implemented")
    }




}