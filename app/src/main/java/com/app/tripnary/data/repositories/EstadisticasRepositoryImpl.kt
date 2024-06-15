package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.EstadisticasServiceImpl
import com.app.tripnary.data.api.servicesimpl.UsuariosServiceImpl
import com.app.tripnary.domain.models.EstadisticasModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.EstadisticasRepository
import com.app.tripnary.domain.repositories.PlanViajeRepository
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class EstadisticasRepositoryImpl : EstadisticasRepository {

    private val estadisticasService= EstadisticasServiceImpl()

    override suspend fun getEstadisticasUsuario(reference: String): EstadisticasModel {
        return suspendCoroutine { continuation ->
            val callback: (EstadisticasModel?) -> Unit = { estadisticas ->
                if (estadisticas != null) {

                    continuation.resume(estadisticas)
                } else {
                    continuation.resumeWithException(Throwable("Estadisticas no encontradas"))
                }
            }

            estadisticasService.getEstadisticasUsuario(reference, callback)
        }
    }
}