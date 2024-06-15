package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.VuelosModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface VuelosService {
    @Headers("Content-Type: application/json")
    @GET("vuelos")
    fun getVuelosRecomendados(
        @Query("vuelosRecomendados") vuelosRecomendados: Boolean,
        @Query("originLocationCode") originLocationCode:String,
        @Query("destinationLocationCode") destinationLocationCode: String,
        @Query("departureDate") departureDate:String,
        @Query("adults") adults:Int,
        @Query("returnDate") returnDate:String,
        @Query("max") max:Int): Call<List<VuelosModel>>



}