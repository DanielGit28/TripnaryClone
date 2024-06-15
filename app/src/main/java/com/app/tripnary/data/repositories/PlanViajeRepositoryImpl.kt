package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.PlanViajeServiceImpl
import com.app.tripnary.data.api.servicesimpl.TaskServiceImpl
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.datasources.DatabaseTaskDataSource
import com.app.tripnary.data.mappers.PlanViajeMapper.noteEntityFromModel
import com.app.tripnary.data.mappers.PlanViajeMapper.toPlanEntityList
import com.app.tripnary.data.mappers.PlanViajeMapper.toPlanModelList
import com.app.tripnary.data.mappers.TaskMapper.noteEntityFromModel
import com.app.tripnary.data.mappers.TaskMapper.toTaskEntityList
import com.app.tripnary.data.mappers.TaskMapper.toTaskModelList
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.repositories.PlanViajeRepository
import com.app.tripnary.domain.repositories.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PlanViajeRepositoryImpl(private val planDataSource: DatabasePlanesViajesDataSource) :
    PlanViajeRepository {

    private val planViajeService = PlanViajeServiceImpl()

    override suspend fun addPlanViaje(planViaje: PlanViajeModel) : PlanViajeModel {

        return suspendCoroutine { continuation ->
            val callback: (PlanViajeModel?) -> Unit = { newPlan ->
                if (newPlan != null) {

                    CoroutineScope(Dispatchers.IO).launch {
                        planDataSource.addPlanViaje(planViaje.noteEntityFromModel())
                    }

                    continuation.resume(newPlan)
                } else {
                    continuation.resumeWithException(Throwable("Error al crear el plan"))
                }
            }

            planViajeService.addPlanViaje(planViaje, callback)
        }
    }

    override fun getAllPlanesViajes(): Flow<List<PlanViajeModel>> {
        TODO("Not yet implemented")
    }

    override fun getAllPlanesViajesByReference(reference: String): Flow<List<PlanViajeModel>> {
        planViajeService.getAllPlanesByReference(reference) { planViajeModels ->
            if (planViajeModels != null) {

                CoroutineScope(Dispatchers.IO).launch {
                    planDataSource.clearPlanes()
                    planDataSource.insertPlanes(planViajeModels.toPlanEntityList())
                }

            } else {

            }

        }

        return planDataSource.getAllPlanes()
            .map { planes -> planes.toPlanModelList() }

    }

    override suspend fun updateDiasPlanes(
        reference: String,
        planViaje: PlanViajeModel
    ): PlanViajeModel {
        return suspendCoroutine { continuation ->
            val callback: (PlanViajeModel?) -> Unit = { planUpdate ->
                if (planUpdate != null) {

                    //CoroutineScope(Dispatchers.IO).launch {
                        //planDataSource.updatePlanesDias(planUpdate.noteEntityFromModel().reference, planUpdate.noteEntityFromModel())
                    //}

                    continuation.resume(planUpdate)
                } else {
                    continuation.resumeWithException(Throwable("Error al actualizar el plan"))
                }
            }

            planViajeService.updateDiasPlanViaje(reference, planViaje, callback)
        }
    }

    override suspend fun updatePlanViaje(planViaje: PlanViajeModel): PlanViajeModel {
        return suspendCoroutine { continuation ->
            val callback: (PlanViajeModel?) -> Unit = { newPlan ->
                if (newPlan != null) {


                    continuation.resume(newPlan)
                } else {
                    continuation.resumeWithException(Throwable("Error al crear el plan"))
                }
            }

            planViajeService.updatePlanViaje(planViaje, callback)
        }
    }

    override suspend fun deletePlanViaje(reference: String): PlanViajeModel {
        return suspendCoroutine { continuation ->
            val callback: (PlanViajeModel?) -> Unit = { planViaje ->
                if (planViaje != null) {

                    continuation.resume(planViaje)
                } else {
                    continuation.resumeWithException(Throwable("Error al eliminar el plan"))
                }
            }

            planViajeService.deletePlanViaje(reference, callback)
        }
    }


}