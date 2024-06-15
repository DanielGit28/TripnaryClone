package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.HotelesModel
import com.app.tripnary.domain.models.VuelosModel
import kotlinx.coroutines.flow.Flow

interface HotelesRepository {
    fun getHotelesRecomendados(hoteles:Boolean, latitud: String, longitud: String, radio: Number): Flow<List<HotelesModel>>
}