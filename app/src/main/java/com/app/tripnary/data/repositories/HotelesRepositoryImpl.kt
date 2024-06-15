package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.HotelesServiceImpl
import com.app.tripnary.domain.models.HotelesModel
import com.app.tripnary.domain.repositories.HotelesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HotelesRepositoryImpl:HotelesRepository {
    private val hotelesService = HotelesServiceImpl()

    override fun getHotelesRecomendados(
        hotelesRecomendados: Boolean, latitud: String,
        longitud: String, radio: Number
    ): Flow<List<HotelesModel>> = flow {
        val hotelesModel = suspendCoroutine<List<HotelesModel>?> { continuation ->
            hotelesService.getHotelesRecomendados(hotelesRecomendados, latitud, longitud, radio,
                { hotel ->
                    continuation.resume(hotel)
                })
        }

        if (hotelesModel != null) {
            emit(hotelesModel)
        }
    }.flowOn(Dispatchers.IO)
}