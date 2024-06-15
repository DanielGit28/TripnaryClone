package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.CiudadRepository
import com.app.tripnary.domain.repositories.PlanViajeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListCiudadesPaisUseCase (private val ciudadRepository: CiudadRepository) {

    fun execute(idPais: String): Flow<List<CiudadModel>> =
        ciudadRepository.getAllCiudadesByPais(idPais)
            .map { ciudad -> ciudad.sortedBy { ciudad -> ciudad.reference } }
}