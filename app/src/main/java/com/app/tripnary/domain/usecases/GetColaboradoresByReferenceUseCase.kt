package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.repositories.ColaboradoresRepository

class GetColaboradoresByReferenceUseCase(private val colaboradoresRepository: ColaboradoresRepository) {

    suspend fun execute(reference: String) : ColaboradoresModel {
        return colaboradoresRepository.getColaboradoresByReference(reference)
    }
}