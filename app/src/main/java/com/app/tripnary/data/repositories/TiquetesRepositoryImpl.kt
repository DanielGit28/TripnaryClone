package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.RestaurantesRecomendadosAIServiceImpl
import com.app.tripnary.data.api.servicesimpl.TiquetesServiceImpl
import com.app.tripnary.domain.models.InteresesRestaurantesAIModel
import com.app.tripnary.domain.models.TiquetesModel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TiquetesRepositoryImpl {
    private val tiquetesService = TiquetesServiceImpl()

    suspend fun addTiquete(tiquete: TiquetesModel): String {
        return suspendCoroutine { continuation ->
            val callback: (String) -> Unit = {tiquete ->
                if(tiquete != null) {

                    continuation.resume(tiquete)
                } else {
                    continuation.resumeWithException(Throwable("Error con obtener recomendaciones ai"))
                }

            }
            tiquetesService.addTiquete(tiquete, callback)
        }

    }
}