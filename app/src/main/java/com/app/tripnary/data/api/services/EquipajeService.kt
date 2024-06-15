package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface EquipajeService {

    @Headers("Content-Type: application/json")
    @GET("equipaje")
    fun getAllEquipajeByPlan(@Query("idPlanViaje") idPlanViaje: String): Call<List<EquipajeModel>>

    @Headers("Content-Type: application/json")
    @POST("equipaje")
    fun postEquipaje(@Body data: EquipajeModel) : Call<EquipajeModel>

    @Headers("Content-Type: application/json")
    @PUT("equipaje")
    fun updateEquipaje(@Body data: EquipajeModel) : Call<EquipajeModel>

    @Headers("Content-Type: application/json")
    @DELETE("equipaje")
    fun deleteEquipaje(@Query("reference") reference:String): Call<EquipajeModel>

}