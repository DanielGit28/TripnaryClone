package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.HotelesModel
import com.app.tripnary.domain.repositories.HotelesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetHotelesRecomendadoUseCase(private val hotelesRepository: HotelesRepository) {

    fun execute(hotelesRecomendados:Boolean, latitud: String, longitud: String, radio: Number): Flow<List<HotelesModel>> =
        hotelesRepository.getHotelesRecomendados(hotelesRecomendados, latitud, longitud, radio)
            .map { pais -> pais.sortedBy { hotel -> hotel.puntuacion } }
}