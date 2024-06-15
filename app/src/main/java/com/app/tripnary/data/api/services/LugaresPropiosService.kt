package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LugaresPropiosService {

    @Headers("Content-Type: application/json")
    @POST("lugaresPropios")
    fun postLugaresPropios(@Body data: LugarPropioModel) : Call<LugarPropioModel>

    @Headers("Content-Type: application/json")
    @GET("lugaresPlanes")
    fun getAllByLugaresPropios(@Query("idPlanViajeLugar") idPlanViajeLugar: String): Call<List<LugarPropioModel>>

}