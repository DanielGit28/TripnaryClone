package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.HotelesModel
import com.app.tripnary.domain.models.VuelosModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface HotelesService {
    @Headers("Content-Type: application/json")
    @GET("hoteles")
    fun getHotelesRecomendados(
        @Query("hotelesRecomendados") hotelesRecomendados: Boolean = true,
        @Query("latitud") latitud: String,
        @Query("longitud") longitud: String,
        @Query("radio") radio: Number): Call<List<HotelesModel>>
}