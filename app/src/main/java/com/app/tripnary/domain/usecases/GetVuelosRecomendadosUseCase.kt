package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.models.VuelosModel
import com.app.tripnary.domain.repositories.VuelosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetVuelosRecomendadosUseCase(private val vuelosRepository: VuelosRepository) {

    fun execute(vuelosRecomendados:Boolean, originLocationCode:String,
                destinationLocationCode: String, departureDate:String, adults:Int,
                returnDate:String, max:Int): Flow<List<VuelosModel>> =
        vuelosRepository.getVuelosRecomendados(vuelosRecomendados, originLocationCode,
            destinationLocationCode, departureDate, adults, returnDate, max)
            .map { pais -> pais.sortedBy { vuelo -> vuelo.precio } }
}