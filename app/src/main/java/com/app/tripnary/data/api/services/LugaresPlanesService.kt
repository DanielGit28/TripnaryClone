package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.UsuariosModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LugaresPlanesService {

    @Headers("Content-Type: application/json")
    @POST("lugaresPlanes")
    fun postPlanViaje(@Body data: LugarPlanModel) : Call<LugarPlanModel>

    @Headers("Content-Type: application/json")
    @GET("lugaresPlanes")
    fun getAllPlanesByReference(@Query("idPlanViaje") idPlanViaje: String): Call<List<LugarPlanModel>>

    @Headers("Content-Type: application/json")
    @PUT("lugaresPlanes")
    fun updateLugarPlan(@Body data: LugarPlanModel) : Call<LugarPlanModel>

    @Headers("Content-Type: application/json")
    @DELETE("lugaresPlanes")
    fun deleteLugarPlan(@Query("reference") reference:String): Call<LugarPlanModel>

}