package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosRepository
import com.app.tripnary.domain.repositories.PaisRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListLugaresRecomendadosUseCase (private val lugaresRecomendadosRepository: LugaresRecomendadosRepository) {

    fun execute(): Flow<List<LugaresRecomendadosModel>> =
        lugaresRecomendadosRepository.getAllLugares()
            .map { lugar -> lugar.sortedBy { lugar -> lugar.reference } }
}