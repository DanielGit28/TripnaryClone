package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.LugarPlanServiceImpl
import com.app.tripnary.data.api.servicesimpl.PlanViajeServiceImpl
import com.app.tripnary.data.datasources.DatabaseLugarPlanesDataSource
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.mappers.LugarPlanesMapper.lugarPlanEntityFromModel
import com.app.tripnary.data.mappers.LugarPlanesMapper.toLugarPlanEntityList
import com.app.tripnary.data.mappers.LugarPlanesMapper.toLugarPlanModelList
import com.app.tripnary.data.mappers.PlanViajeMapper.noteEntityFromModel
import com.app.tripnary.data.mappers.PlanViajeMapper.toPlanEntityList
import com.app.tripnary.data.mappers.PlanViajeMapper.toPlanModelList
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.LugarPlanesRepository
import com.app.tripnary.domain.repositories.PlanViajeRepository
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

class LugarPlanesRepositoryImpl(private val lugarPlanesDataSource: DatabaseLugarPlanesDataSource) :
    LugarPlanesRepository {

    private val lugarPlanServiceImpl = LugarPlanServiceImpl()

    override suspend fun addLugarPlanes(lugarPlanModel: LugarPlanModel): LugarPlanModel {
        return suspendCoroutine { continuation ->
            val callback: (LugarPlanModel?) -> Unit = { lugarPlan ->
                if (lugarPlan != null) {

                    CoroutineScope(Dispatchers.IO).launch {
                        lugarPlanesDataSource.addLugarPlan(lugarPlan.lugarPlanEntityFromModel())
                    }

                    continuation.resume(lugarPlan)
                } else {
                    continuation.resumeWithException(Throwable("Error al actualizar el plan"))
                }
            }

            lugarPlanServiceImpl.addLugarPlanViaje(lugarPlanModel, callback)
        }
    }

    override fun getAllPlanesLugaresByReference(idPlanViaje: String): Flow<List<LugarPlanModel>> {
        lugarPlanServiceImpl.getAllPlanesByReference(idPlanViaje) { planViajeModels ->
            if (planViajeModels != null) {

                CoroutineScope(Dispatchers.IO).launch {
                    lugarPlanesDataSource.clearLugares()
                    lugarPlanesDataSource.insertAllLugares(planViajeModels.toLugarPlanEntityList())
                }

            } else {

            }

        }

        return lugarPlanesDataSource.getAllLugarPlanes()
            .map { planes -> planes.toLugarPlanModelList() }

    }

    override suspend fun updateLugarPlanes(lugarPlanModel: LugarPlanModel): LugarPlanModel {
        return suspendCoroutine { continuation ->
            val callback: (LugarPlanModel?) -> Unit = { lugarPlan ->
                if (lugarPlan != null) {


                    continuation.resume(lugarPlan)
                } else {
                    continuation.resumeWithException(Throwable("Error al actualizar el plan"))
                }
            }

            lugarPlanServiceImpl.updateLugarPlanViaje(lugarPlanModel, callback)
        }
    }

    override suspend fun deleteLugarPlan(reference: String): LugarPlanModel {
        return suspendCoroutine { continuation ->
            val callback: (LugarPlanModel?) -> Unit = { lugarPlan ->
                if (lugarPlan != null) {

                    continuation.resume(lugarPlan)
                } else {
                    continuation.resumeWithException(Throwable("Error al eliminar el plan"))
                }
            }

            lugarPlanServiceImpl.deleteLugarPlanByReference(reference, callback)
        }
    }
}