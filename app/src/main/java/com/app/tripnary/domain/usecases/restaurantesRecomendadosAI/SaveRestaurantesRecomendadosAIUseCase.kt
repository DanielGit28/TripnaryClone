package com.app.tripnary.domain.usecases.restaurantesRecomendadosAI

import com.app.tripnary.data.repositories.RestaurantesRecomendadosAIRepositoryImpl
import com.app.tripnary.domain.models.InteresesRestaurantesAIModel

class SaveRestaurantesRecomendadosAIUseCase(private val restaurantesRecomendadosAIRepository: RestaurantesRecomendadosAIRepositoryImpl) {

    suspend fun execute(interes: InteresesRestaurantesAIModel, referencePlanViaje: String, tipo: String) : String {
        return restaurantesRecomendadosAIRepository.addPrompt(interes,referencePlanViaje,tipo)
    }
}