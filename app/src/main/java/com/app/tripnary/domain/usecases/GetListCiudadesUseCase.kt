package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.repositories.CiudadRepository
import com.app.tripnary.domain.repositories.ContinenteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListCiudadesUseCase (private val ciudadRepository: CiudadRepository) {

    fun execute(): Flow<List<CiudadModel>> =
        ciudadRepository.getAllCiudades()
            .map { ciudad -> ciudad.sortedBy { ciudad -> ciudad.reference } }
}