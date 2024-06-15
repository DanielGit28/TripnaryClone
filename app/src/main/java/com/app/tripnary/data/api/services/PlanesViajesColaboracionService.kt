package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.PlanViajeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PlanesViajesColaboracionService {
    @Headers("Content-Type: application/json")
    @GET("planesViajesColaboracion")
    fun getTipoColaboradorByIdPlanViaje(@Query("idPlanViaje") idPlanViaje: String): Call<List<ColaboradoresModel>>
    @Headers("Content-Type: application/json")
    @GET("planesViajesColaboracion")
    fun getAllPlanesByCorreoColaborador(@Query("correoColaborador") correoColaborador: String): Call<List<PlanViajeModel>>
}