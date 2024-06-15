package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.InteresLugaresAIModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LugaresRecomendadosAIService {


    @Headers("Content-Type: application/json")
    @GET("lugaresRecomendadosAIInteres")
    fun getLugaresRecomendadosAIByInteres(@Query("idInteres") reference: String) : Call<List<InteresLugaresAIModel>>

    @Headers("Content-Type: application/json")
    @GET("lugarRecomendadoAI")
    fun getLugarRecomendadoAIById (@Query("reference") reference: String) : Call<InteresLugaresAIModel>


    @Headers("Content-Type: application/json")
    @GET("lugaresRecomendadosAI")
    fun getAllLugares() : Call<List<InteresLugaresAIModel>>

    @Headers("Content-Type: application/json")
    @POST("lugarRecomendadoAI")
    fun postLugarRecomendadoAI(@Body data: InteresLugaresAIModel) : Call<InteresLugaresAIModel>

    @Headers("Content-Type: application/json")
    @POST("prompts")
    fun postPrompt(@Body data: InteresLugaresAIModel, @Query("referencePlanViaje") referencePlanViaje: String, @Query("tipo") tipo: String) : Call<String>

}