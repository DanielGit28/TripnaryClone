package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.PlanViajeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CiudadesService {

    @Headers("Content-Type: application/json")
    @GET("ciudades")
    fun getAllCiudades() : Call<List<CiudadModel>>

    @Headers("Content-Type: application/json")
    @GET("ciudades")
    fun getAllCiudadesByPais(@Query("idPais") idPais: String): Call<List<CiudadModel>>
}