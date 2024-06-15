package com.app.tripnary.domain.usecases.lugaresRecomendadosAI


import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosAIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListLugaresRecomendadosAIUseCase (private val lugaresRecomendadosAIRepository: LugaresRecomendadosAIRepository) {

    fun execute(): Flow<List<InteresLugaresAIModel>> =
        lugaresRecomendadosAIRepository.getAllLugares()
            .map { lugar -> lugar.sortedBy { lugar -> lugar.reference } }
}