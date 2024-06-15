package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LugaresRecomendadosService {
    @Headers("Content-Type: application/json")
    @GET("lugaresRecomendados")
    fun getLugaresRecomendadosByIntereses(@Query("reference") reference: String) : Call<List<LugaresRecomendadosModel>>

    @Headers("Content-Type: application/json")
    @GET("lugares")
    fun getLugaresRecomendadosById (@Query("reference") reference: String) : Call<LugaresRecomendadosModel>

    @Headers("Content-Type: application/json")
    @GET("lugaresRecomendados")
    fun getLugaresRecomendadosByPopularidad() : Call<List<LugaresRecomendadosModel>>

    @Headers("Content-Type: application/json")
    @GET("lugares")
    fun getAllLugares() : Call<List<LugaresRecomendadosModel>>

    @Headers("Content-Type: application/json")
    @POST("lugaresPropios")
    fun postLugaresPropios(@Body data: LugaresRecomendadosModel) : Call<LugaresRecomendadosModel>

    @Headers("Content-Type: application/json")
    @GET("lugaresPlanes")
    fun getAllByLugaresRecomendados(@Query("idPlanViajeLugarRecomendado") idPlanViajeLugarRecomendado: String): Call<List<LugaresRecomendadosModel>>
}