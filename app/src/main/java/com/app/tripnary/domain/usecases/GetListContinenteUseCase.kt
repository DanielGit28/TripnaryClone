package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.repositories.ContinenteRepository
import com.app.tripnary.domain.repositories.PaisRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListContinenteUseCase (private val continenteRepository: ContinenteRepository) {

    fun execute(): Flow<List<ContinenteModel>> =
        continenteRepository.getAllContinentes()
            .map { continente -> continente.sortedBy { continente -> continente.reference } }
}