package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.InteresesRestaurantesAIModel
import com.app.tripnary.domain.models.TiquetesModel

interface TiquetesRepository {
    suspend fun addTiquete(tiquete: TiquetesModel): String
}