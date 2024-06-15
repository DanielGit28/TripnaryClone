package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.RestaurantesRecomendadosAIServiceImpl
import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.app.tripnary.domain.models.InteresesRestaurantesAIModel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RestaurantesRecomendadosAIRepositoryImpl {
    private val lugaresRecomendadosService = RestaurantesRecomendadosAIServiceImpl()
    suspend fun addPrompt(interesAI: InteresesRestaurantesAIModel, referencePlanViaje: String, tipo: String): String {
        return suspendCoroutine { continuation ->
            val callback: (String) -> Unit = {recomendaciones ->
                if(recomendaciones != null) {

                    continuation.resume(recomendaciones)
                } else {
                    continuation.resumeWithException(Throwable("Error con obtener recomendaciones ai"))
                }

            }
            lugaresRecomendadosService.postPrompt(interesAI, referencePlanViaje, tipo, callback)
        }

    }
}