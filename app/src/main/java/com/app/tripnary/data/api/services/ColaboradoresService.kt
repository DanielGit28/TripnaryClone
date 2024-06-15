package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.ContinenteModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ColaboradoresService {
    @Headers("Content-Type: application/json")
    @GET("colaboradoresViajes")
    fun getColaboradoresByViaje(@Query("idPlanViaje") idPlanViaje: String) : Call<List<ColaboradoresModel>>

    @Headers("Content-Type: application/json")
    @POST("colaboradoresViajes")
    fun postColaboradores(@Body colaboradoresData: ColaboradoresModel) : Call<ColaboradoresModel>

    @Headers("Content-Type: application/json")
    @GET("colaboradoresViajes")
    fun getColaboradoresByReference(@Query("reference") reference: String) : Call<ColaboradoresModel>

    @Headers("Content-Type: application/json")
    @PUT("colaboradoresViajes")
    fun updateColaborador(@Body colaboradoresData: ColaboradoresModel) : Call<ColaboradoresModel>
}