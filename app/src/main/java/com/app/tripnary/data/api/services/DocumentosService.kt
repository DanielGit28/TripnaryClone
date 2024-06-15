package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.DocumentosModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface DocumentosService {
    @Headers("Content-Type: application/json")
    @POST("documentos")
    fun postDocumentos(@Body documentosModel: DocumentosModel) : Call<DocumentosModel>

    @Headers("Content-Type: application/json")
    @GET("documentos")
    fun getDocumentosByIdPlanViaje(@Query("idPlanViaje") idPlanViaje: String): Call<List<DocumentosModel>>

    @Headers("Content-Type: application/json")
    @GET("documentos")
    fun getDocumentosByReference(@Query("reference") reference: String): Call<DocumentosModel>

    @Headers("Content-Type: application/json")
    @PUT("documentos")
    fun updateDocumentos(@Body documentosModel: DocumentosModel) : Call<DocumentosModel>
}