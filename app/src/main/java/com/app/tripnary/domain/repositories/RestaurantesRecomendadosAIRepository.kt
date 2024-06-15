package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.InteresesRestaurantesAIModel

interface RestaurantesRecomendadosAIRepository {
    suspend fun addPrompt(interesRestauranteAI: InteresesRestaurantesAIModel, referencePlanViaje: String, tipo: String): String
}