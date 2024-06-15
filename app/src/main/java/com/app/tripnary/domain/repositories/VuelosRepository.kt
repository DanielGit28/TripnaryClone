package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.models.VuelosModel
import kotlinx.coroutines.flow.Flow

interface VuelosRepository {
    fun getVuelosRecomendados(vuelosRecomendados:Boolean, originLocationCode:String,
                              destinationLocationCode: String, departureDate:String, adults:Int,
                              returnDate:String, max:Int): Flow<List<VuelosModel>>
}