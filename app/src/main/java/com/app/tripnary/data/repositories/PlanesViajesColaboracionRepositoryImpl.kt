package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.PlanesViajesColaboracionServiceImpl
import com.app.tripnary.data.datasources.DatabasePlanesViajesColaboradorDataSource
import com.app.tripnary.data.mappers.PlanViajeMapper.toPlanColaboradoresEntityList
import com.app.tripnary.data.mappers.PlanViajeMapper.toPlanColaboradoresModelList
import com.app.tripnary.data.mappers.PlanViajeMapper.toPlanEntityList
import com.app.tripnary.data.mappers.PlanViajeMapper.toPlanModelList
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.PlanesViajesColaboracionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PlanesViajesColaboracionRepositoryImpl(private val planesViajesColaboradorDataSource: DatabasePlanesViajesColaboradorDataSource) : PlanesViajesColaboracionRepository {

    private val planesViajesColaboracionService = PlanesViajesColaboracionServiceImpl()
    override suspend fun getTipoColaboradorByIdPlanViaje(idPlanViaje: String): List<ColaboradoresModel> {
        return suspendCoroutine { continuation ->
            planesViajesColaboracionService.getTipoColaboradorByIdPlanViaje(idPlanViaje) { tipo, error ->
                if (error != null){
                    continuation.resumeWithException(error as Throwable)
                } else {
                    val tipoToResume = tipo ?: listOf(
                        ColaboradoresModel(
                            reference = "",
                            correoElectronico = "",
                            estado = "",
                            rol = "Administrador",
                            idPlanViaje = "",
                            nombre = "",
                            idUsuarioColaborador = ""
                        )
                    )
                    continuation.resume(tipoToResume)
                }
            }
        }
    }
    override suspend fun getPlanesByCorreoColaborador(correo: String): Flow<List<PlanViajeModel>> {
        planesViajesColaboracionService.getPlanesByCorreoColaborador(correo) { planViajeModels ->
            if (planViajeModels != null) {

                CoroutineScope(Dispatchers.IO).launch {
                    planesViajesColaboradorDataSource.clearPlanes()
                    planesViajesColaboradorDataSource.insertPlanes(planViajeModels.toPlanColaboradoresEntityList())

                }
            } else {

            }
        }

        return planesViajesColaboradorDataSource.getAllPlanes()
            .map { planes -> planes.toPlanColaboradoresModelList() }

    }


}