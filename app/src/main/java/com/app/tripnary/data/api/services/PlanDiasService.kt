package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.models.PlanDiasModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PlanDiasService {

    @Headers("Content-Type: application/json")
    @GET("diasPlanesViajes")
    fun getAllDias() : Call<List<PlanDiasModel>>

    @Headers("Content-Type: application/json")
    @GET("diasPlanesViajes")
    fun getDiasByIdPlanViaje(@Query("idPlanViaje") idPlanVIaje: String?) : Call<List<PlanDiasModel>>
}