package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.EstadisticasModel
import com.app.tripnary.domain.models.PlanViajeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface EstadisticasService {

    @Headers("Content-Type: application/json")
    @GET("estadisticasUsuario")
    fun getEstadisticas(@Query("reference") reference:String): Call<EstadisticasModel>
}