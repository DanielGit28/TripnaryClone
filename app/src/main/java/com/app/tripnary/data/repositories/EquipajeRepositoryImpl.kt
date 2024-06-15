package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.CiudadServiceImpl
import com.app.tripnary.data.api.servicesimpl.EquipajeServiceImpl
import com.app.tripnary.data.mappers.PlanViajeMapper.noteEntityFromModel
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.CiudadRepository
import com.app.tripnary.domain.repositories.EquipajeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class EquipajeRepositoryImpl : EquipajeRepository {

    private val equipajeService = EquipajeServiceImpl()

    override fun getAllEquipajeByPlan(idPlanViaje: String): Flow<List<EquipajeModel>> = flow {
        val equipajeModel = suspendCoroutine<List<EquipajeModel>?> { continuation ->
            equipajeService.getAllEquipajeByPlan(idPlanViaje) { equipaje ->
                continuation.resume(equipaje)
            }
        }


        if (equipajeModel != null) {
            emit(equipajeModel)
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun addEquipaje(equipajeModel: EquipajeModel) : EquipajeModel {

        return suspendCoroutine { continuation ->
            val callback: (EquipajeModel?) -> Unit = { newEquipaje ->
                if (newEquipaje != null) {


                    continuation.resume(newEquipaje)
                } else {
                    continuation.resumeWithException(Throwable("Error al crear el equipaje"))
                }
            }

            equipajeService.addEquipaje(equipajeModel, callback)
        }
    }

    override suspend fun updateEquipaje(equipajeModel: EquipajeModel): EquipajeModel {
        return suspendCoroutine { continuation ->
            val callback: (EquipajeModel?) -> Unit = { newEquipaje ->
                if (newEquipaje != null) {


                    continuation.resume(newEquipaje)
                } else {
                    continuation.resumeWithException(Throwable("Error al crear el equipaje"))
                }
            }

            equipajeService.updateEquipaje(equipajeModel, callback)
        }
    }

    override suspend fun deleteEquipaje(reference: String): EquipajeModel {
        return suspendCoroutine { continuation ->
            val callback: (EquipajeModel?) -> Unit = { equipaje ->
                if (equipaje != null) {

                    continuation.resume(equipaje)
                } else {
                    continuation.resumeWithException(Throwable("Error al eliminar el equipaje"))
                }
            }

            equipajeService.deleteEquipaje(reference, callback)
        }
    }


}