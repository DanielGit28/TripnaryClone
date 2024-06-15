package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.ListaDeseosModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ListaDeseosService {
    @Headers("Content-Type: application/json")
    @GET("listaDeseos")
    fun getAllListaDeseos(@Query("idUsuario") referenceUsuario: String?) : Call<List<ListaDeseosModel>>

    @Headers("Content-Type: application/json")
    @POST("listaDeseos")
    fun postListaDeseos(@Body data: ListaDeseosModel) : Call<ListaDeseosModel>

    @Headers("Content-Type: application/json")
    @DELETE("listaDeseos")
    fun deleteListaDeseos(@Query("reference") reference:String): Call<ListaDeseosModel>
}