package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.repositories.ColaboradoresRepository

class UpdateColaboradorUseCase(private val colaboradoresRepository: ColaboradoresRepository) {

    suspend fun execute(colaboradoresModel: ColaboradoresModel) {
        colaboradoresRepository.updateColaborador(colaboradoresModel)
    }
}