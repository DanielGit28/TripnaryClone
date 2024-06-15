package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.repositories.ColaboradoresRepository

class AddColaboradorUseCase(private val colaboradoresRepository: ColaboradoresRepository) {

    suspend fun execute(colaboradoresModel: ColaboradoresModel) {
        colaboradoresRepository.addColaboradores(colaboradoresModel)
    }
}