package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.LugarPlanServiceImpl
import com.app.tripnary.data.api.servicesimpl.LugarPropioServiceImpl
import com.app.tripnary.data.datasources.DatabaseLugarPlanesDataSource
import com.app.tripnary.data.datasources.DatabaseLugaresPropiosDataSource
import com.app.tripnary.data.mappers.LugaresPropiosMapper.toLugarEntityList
import com.app.tripnary.data.mappers.LugaresPropiosMapper.toLugarPropioModelList
import com.app.tripnary.data.mappers.LugaresRecomendadosMapper.toLugaresRecomendadosEntityList
import com.app.tripnary.data.mappers.LugaresRecomendadosMapper.toLugaresRecomendadosModelList
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.repositories.LugarPlanesRepository
import com.app.tripnary.domain.repositories.LugarPropioRepository
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

class LugarPropioRepositoryImpl (private val databaseLugaresPropiosDataSource: DatabaseLugaresPropiosDataSource) :
    LugarPropioRepository {

    private val lugarPropioServiceImpl = LugarPropioServiceImpl()

    override suspend fun addLugarPropio(lugarPropioModel: LugarPropioModel): LugarPropioModel {
        return suspendCoroutine { continuation ->
            val callback: (LugarPropioModel?) -> Unit = { lugarPropio ->
                if (lugarPropio != null) {

                    continuation.resume(lugarPropio)
                } else {
                    continuation.resumeWithException(Throwable("Error al actualizar el lugar"))
                }
            }

            lugarPropioServiceImpl.addLugarPropio(lugarPropioModel, callback)
        }

    }

    override fun getAllPlanesLugaresByPropios(idPlanViajeLugar: String): Flow<List<LugarPropioModel>> {
        lugarPropioServiceImpl.getAllLugarPlanByPropios(idPlanViajeLugar) { lugares ->
            if (lugares != null) {

                CoroutineScope(Dispatchers.IO).launch {
                    databaseLugaresPropiosDataSource.clearLugares()
                    databaseLugaresPropiosDataSource.insertAllLugares(lugares.toLugarEntityList())
                }

            } else {

            }

        }

        return databaseLugaresPropiosDataSource.getAllLugarPropios()
            .map { planes -> planes.toLugarPropioModelList() }

    }
}