package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.InteresesRestaurantesAIModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RestaurantesRecomendadosAIService {
    @Headers("Content-Type: application/json")
    @POST("prompts")
    fun postPrompt(@Body data: InteresesRestaurantesAIModel, @Query("referencePlanViaje") referencePlanViaje: String, @Query("tipo") tipo: String) : Call<String>
}