package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.ContinenteModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ContinentesService {

    @Headers("Content-Type: application/json")
    @GET("continentes")
    fun getAllContinentes() : Call<List<ContinenteModel>>
}