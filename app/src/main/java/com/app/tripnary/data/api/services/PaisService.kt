package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.PaisModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface PaisService {

    @Headers("Content-Type: application/json")
    @GET("paises")
    fun getAllPaises() : Call<List<PaisModel>>
}