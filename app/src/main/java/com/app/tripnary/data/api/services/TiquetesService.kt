package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.InteresesRestaurantesAIModel
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.models.TiquetesModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TiquetesService {
    @Headers("Content-Type: application/json")
    @POST("tiquetes")
    fun addTiquete(@Body data: TiquetesModel, @Query("agregarTiquete") agregarTiquete: Boolean = true) : Call<String>

}