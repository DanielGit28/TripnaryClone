package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.TaskModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PlanViajeService {

    @Headers("Content-Type: application/json")
    @POST("planesViajes")
    fun postPlanViaje(@Body planData: PlanViajeModel) : Call<PlanViajeModel>

    @Headers("Content-Type: application/json")
    @GET("planesViajes")
    fun getAllPlanes() : Call<List<PlanViajeModel>>

    @Headers("Content-Type: application/json")
    @GET("planesViajes")
    fun getAllPlanesByReference(@Query("reference") reference: String): Call<List<PlanViajeModel>>

    @Headers("Content-Type: application/json")
    @PUT("planesViajes")
    fun updateDiasPlanes(@Query("updatePlanDias") reference: String, @Body planViajeModel: PlanViajeModel): Call<PlanViajeModel>

    @Headers("Content-Type: application/json")
    @PUT("planesViajes")
    fun updatePlanViaje(@Body data: PlanViajeModel) : Call<PlanViajeModel>

    @Headers("Content-Type: application/json")
    @DELETE("planesViajes")
    fun deletePlan(@Query("reference") reference:String): Call<PlanViajeModel>

}